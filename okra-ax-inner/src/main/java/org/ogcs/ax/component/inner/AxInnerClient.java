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

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.ax.component.AxComponent;
import org.ogcs.ax.component.GpbClient;
import org.ogcs.ax.component.SpringContext;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.AxShard;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import org.ogcs.ax.gpb.OkraAx.AxOutbound;
import org.ogcs.ax.gpb.OkraAx.AxReqAuth;


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

    private final String module;
    private final long id;
    private final long local;

    public AxInnerClient(String module, long id, long local, String host, int port) {
        super(host, port, true);
        this.module = module;
        this.local = local;
        this.id = id;
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
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, AxOutbound msg) {
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
        // 移除
        AxShard axCoShard = axCoManager.getAxCoShard(module);
        if (axCoShard != null) {
            axCoManager.removeByModule(module, String.valueOf(id));
        }
        super.connectionInactive(ctx);
    }
}
