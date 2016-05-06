/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component.inner;

import com.google.protobuf.ByteString;
import com.lj.kernel.gpb.GpbD.Request;
import io.netty.buffer.Unpooled;
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
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxInnerClient extends GpbClient<AxOutbound> implements AxComponent {

    private static final Logger LOG = LogManager.getLogger(AxInnerClient.class);
    private static final ProtobufDecoder AX_OUTBOUND_DECODER = new ProtobufDecoder(AxOutbound.getDefaultInstance());
    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private AxInnerCoManager axCoManager = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    private final AxCoInfo info;
    private final String module;
    private final long id;
    private final long local;

    public AxInnerClient(String module, long local, AxCoInfo info) {
        super(info.getHost(), info.getPort(), true);
        this.local = local;
        this.module = module;
        this.id = info.getId();
        this.info = info;
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
        session.writeAndFlush(
                AxInbound.newBuilder()
                        .setRid(REQUEST_ID.getAndIncrement())
                        .setCmd(1000) // 授权  INNER_AUTH
                        .setSource(local)
                        .setData(
                                AxReqAuth.newBuilder()
                                        .setKey("ABCD")
                                        .setSource(local)
                                        .build().toByteString()
                        )
                        .build()
        );
        ByteString abcd = AxReqAuth.newBuilder()
                .setKey("ABCD")
                .setSource(local)
                .build().toByteString();
        request(local, 1000, abcd, (t)-> {
            System.out.println();
        });
        // register to component manager
        axCoManager.add(module, this);
    }

    private static final Map<Integer, AxCallback> callbacks = new ConcurrentHashMap<>();

    public void request(long source, int cmd, ByteString msg, AxCallback callback) {
        int rid = REQUEST_ID.getAndIncrement();
        if (callback != null)
            callbacks.put(rid, callback);
        transport(rid, cmd, source, msg);
    }

    public void push(int cmd, ByteString msg) {
        transport(REQUEST_ID.getAndIncrement(), cmd, -1, msg);
    }

    public void transport(int rid, int cmd, long source, ByteString msg) {
        if (session == null) {
            throw new NullPointerException("session");
        }
        session.writeAndFlush(
                AxInbound.newBuilder()
                        .setRid(rid)
                        .setCmd(cmd) // 授权  INNER_AUTH
                        .setSource(source)
                        .setData(msg)
                        .build()
        );
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, AxOutbound msg) {
        AxCallback callback = callbacks.remove(msg.getRid());
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
            System.out.println(msg.toString());
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
}
