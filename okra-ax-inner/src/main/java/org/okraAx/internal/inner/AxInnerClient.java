/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.okraAx.internal.inner;

import com.google.protobuf.ByteString;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;
import org.okraAx.internal.bean.AxCoInfo;
import org.okraAx.internal.bean.AxShard;
import org.okraAx.internal.core.AxCallback;
import org.okraAx.internal.core.AxComponent;
import org.okraAx.internal.core.AxCodec;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.component.manager.AxInnerCoManager;
import org.okraAx.component.manager.ConnectorManager;
import org.okraAx.internal.config.AxProperties;
import org.okraAx.internal.AxState;
import org.okraAx.internal.SpringContext;
import org.okraAx.utilities.AxReplys;
import org.okraAx.v3.OkraAx;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gate connect to Remote
 * 内部组件 - Client模块
 *
 * @author : TinyZ.
 * @since 1.0
 */
public class AxInnerClient extends GpbClient<OkraAx.AxOutbound> implements AxComponent {

    private static final Logger LOG = LogManager.getLogger(AxInnerClient.class);
    /**
     * 缺省超时时间: 15s
     */
    private static final long DEFAULT_TIME_OUT = 15000;
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final AxGpbCodec AX_OUTBOUND_CODEC = new AxGpbCodec(OkraAx.AxOutbound.getDefaultInstance());

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);

    private final Map<Integer, AxCallback<OkraAx.AxOutbound>> callbacks;
    private final AxCoInfo info;
    private final String module;
    private final long id;
    private final long local;
    private final long timeout;

    private final AxCodec codec;
    private final ChannelHandler axCodecHandler;

    public AxInnerClient(String module, long local, AxCoInfo info) {
        this(module, local, info, AX_OUTBOUND_CODEC);
    }

    public AxInnerClient(String module, long local, AxCoInfo info, AxCodec codec) {
        super(info.getHost(), info.getPort(), true);
        this.local = local;
        this.module = module;
        this.id = info.getId();
        this.info = info;
        this.timeout = DEFAULT_TIME_OUT;
        this.callbacks = new ConcurrentHashMap<>();
        this.codec = codec;
        this.axCodecHandler = new AxCodecHandler(codec);
    }

    public AxCoInfo getInfo() {
        return info;
    }

    @Override
    public String id() {
        return String.valueOf(id);
    }

    @Override
    public void addCodec(ChannelPipeline cp) {
        cp.addLast("axCodec", axCodecHandler);
    }

    @Override
    public void connectionActive(ChannelHandlerContext ctx) {
        super.connectionActive(ctx);
        // 验证访问授权
        push(local, 1000, OkraAx.AxReqAuth.newBuilder()
                .setKey(AxProperties.axInnerAuth)
                .setSource(local)
                .build().toByteString()
        );
        // register to component manager
        axCoManager.add(module, this);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, OkraAx.AxOutbound msg) {
        AxCallback<OkraAx.AxOutbound> callback = callbacks.remove(msg.getRid());
        if (callback != null) {
            callback.run(msg);
            return;
        }
        // 处理没有回调 和 服务方推送消息的情况
        if (msg.hasError()) {
            LOG.warn("Ax Inner Exception [ " + msg.getError().getState() + "] : " + msg.getError().getMsg());
        } else {
            byte[] bytes = msg.getData().toByteArray();
            if (msg.getTargetCount() > 0) {
                connectorManager.pushById(Unpooled.wrappedBuffer(bytes), msg.getTargetList().toArray());
            } else {
                connectorManager.pushAll(Unpooled.wrappedBuffer(bytes));
            }
        }
    }

    @Override
    public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
        // remove component
        AxShard axCoShard = axCoManager.getAxCoShard(module);
        if (axCoShard != null) {
            axCoManager.removeByModule(module, String.valueOf(id));
        }
        // remote server gone away. response all request error.
        synchronized (this) {
            Iterator<Map.Entry<Integer, AxCallback<OkraAx.AxOutbound>>> it = callbacks.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, AxCallback<OkraAx.AxOutbound>> next = it.next();
                next.getValue().run(AxReplys.error(next.getKey(), AxState.STATE_4_SERVER_GONE_AWAY));
                it.remove();
            }
        }
        super.connectionInactive(ctx);
    }

    /**
     * Async request.
     * <p>
     * 使用线程池代理的阻塞request-response模式. 优势:<br/>
     * 1. 超时管理. 避免异步调用等待时间过长，无响应等问题<br/>
     * 2. 避免真异步请求的内存泄漏. [服务方无响应时, callbacks列表中回调未处理的导致的OOM]<br/>
     *
     * @param source request source's id
     * @param cmd    remote service command id.
     * @param msg    request parameter.
     * @param callback callback function
     */
    public void request(long source, int cmd, ByteString msg, AxCallback<OkraAx.AxOutbound> callback) {
        Session session = session();
        if (session != null && session.isOnline()) {
            executor.execute(() -> {
                callback.run(request(source, cmd, msg));
            });
        }
    }

    /**
     * 阻塞的Request-Response模式.
     * <p>
     * 当发送Request后，阻塞线程进行同步等待，持续到服务方Response或等待超时.
     *
     * @param source request source's id
     * @param cmd    remote service command id.
     * @param msg    request parameter.
     * @return Return {@link OkraAx.AxOutbound}.
     */
    public OkraAx.AxOutbound request(long source, int cmd, ByteString msg) {
        BlockingCallback<OkraAx.AxOutbound> callback = new BlockingCallback<>();
        int rid = REQUEST_ID.getAndIncrement();
        callbacks.put(rid, callback);
        transport(rid, cmd, source, msg);
        synchronized (callback) {
            if (!callback.isDone()) { // while(!callback.isDone()) {
                try {
                    callback.wait(this.timeout);
                } catch (InterruptedException e) {
                    LOG.info("Interrupted while blocking.  out of time ", e);
                }
                if (!callback.isDone()) {
                    Thread.currentThread().interrupt();
                    //  break;
                    return AxReplys.error(rid, AxState.STATE_3_REQUEST_TIMEOUT); // 返回请求超时
                }
            }
        }
        return callback.get();
    }

    /**
     * Push message without callback.
     *
     * @param source request source's id
     * @param cmd    remote service command id.
     * @param msg    request parameter.
     */
    public void push(long source, int cmd, ByteString msg) {
        transport(REQUEST_ID.getAndIncrement(), cmd, source, msg);
    }

    private void transport(int rid, int cmd, long source, ByteString msg) {
        if (session == null) {
            throw new NullPointerException("session");
        }
        session.writeAndFlush(
                OkraAx.AxInbound.newBuilder()
                        .setRid(rid)
                        .setCmd(cmd)
                        .setSource(source)
                        .setData(msg)
                        .build()
        );
    }
}
