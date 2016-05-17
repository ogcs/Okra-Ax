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
package org.ogcs.ax.component.inner;

import com.google.protobuf.ByteString;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.*;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.AxShard;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import org.ogcs.ax.gpb.OkraAx.AxOutbound;
import org.ogcs.ax.gpb.OkraAx.AxReqAuth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gate connect to Remote
 * 内部组件 - Client模块
 *
 * @author : TinyZ.
 * @since 1.0
 */
public class AxInnerClient extends GpbClient<AxOutbound> implements AxComponent {

    private static final Logger LOG = LogManager.getLogger(AxInnerClient.class);

    private static final ChannelHandler AX_OUTBOUND_DECODER = new ProtobufDecoder(AxOutbound.getDefaultInstance());

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);

    private static final long DEFAULT_TIME_OUT = 30000;

    private static final Map<Integer, AxCallback<AxOutbound>> callbacks = new ConcurrentHashMap<>();

    private final AxCoInfo info;
    private final String module;
    private final long id;
    private final long local;
    private final long timeout;

    public AxInnerClient(String module, long local, AxCoInfo info) {
        super(info.getHost(), info.getPort(), true);
        this.local = local;
        this.module = module;
        this.id = info.getId();
        this.info = info;
        this.timeout = DEFAULT_TIME_OUT;
    }

    public AxCoInfo getInfo() {
        return info;
    }

    @Override
    public String id() {
        return String.valueOf(id);
    }

    @Override
    public void addGpbDecoder(ChannelPipeline cp) {
        cp.addLast("axOutboundDecoder", AX_OUTBOUND_DECODER);
    }

    @Override
    public void connectionActive(ChannelHandlerContext ctx) {
        super.connectionActive(ctx);
        // 验证访问授权
        push(local, 1000, AxReqAuth.newBuilder()
                .setKey(AxProperties.axInnerAuth)
                .setSource(local)
                .build().toByteString()
        );
        // register to component manager
        axCoManager.add(module, this);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, AxOutbound msg) {
        AxCallback<AxOutbound> callback = callbacks.remove(msg.getRid());
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
        super.connectionInactive(ctx);
    }

    public void request(long source, int cmd, ByteString msg, AxCallback<AxOutbound> callback) {
        int rid = REQUEST_ID.getAndIncrement();
        if (callback != null)
            callbacks.put(rid, callback);
        transport(rid, cmd, source, msg);
    }

    public AxOutbound request(long source, int cmd, ByteString msg) {
        BlockingCallback<AxOutbound> callback = new BlockingCallback<>();
        request(source, cmd, msg, callback);
        synchronized (callback) {
            while (!callback.isDone()) {
                try {
                    callback.wait(this.timeout);
                } catch (InterruptedException e) {
                    LOG.info("Interrupted while blocking.  out of time ", e);
                }
                if (!callback.isDone()) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
        return callback.get();
    }

    /**
     * Push message without callback.
     *
     * @param source source
     * @param cmd    remote method.
     * @param msg    message data.
     */
    public void push(long source, int cmd, ByteString msg) {
        transport(REQUEST_ID.getAndIncrement(), cmd, source, msg);
    }

    private void transport(int rid, int cmd, long source, ByteString msg) {
        if (session == null) {
            throw new NullPointerException("session");
        }
        session.writeAndFlush(
                AxInbound.newBuilder()
                        .setRid(rid)
                        .setCmd(cmd)
                        .setSource(source)
                        .setData(msg)
                        .build()
        );
    }
}
