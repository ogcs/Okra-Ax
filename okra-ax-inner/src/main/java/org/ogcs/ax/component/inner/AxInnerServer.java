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

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.ogcs.ax.component.AxComponent;
import org.ogcs.ax.component.HandlerConst;
import org.ogcs.ax.gpb.OkraAx;
import org.ogcs.netty.impl.TcpProtocolServer;

/**
 * 内部组件 - Server模块
 *
 * @author : TinyZ
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class AxInnerServer extends TcpProtocolServer implements AxComponent {

    private static final ProtobufDecoder AX_INBOUND_DECODER = new ProtobufDecoder(OkraAx.AxInbound.getDefaultInstance());
    private static final AxInnerHandler AX_INNER_HANDLER = new AxInnerHandler();
    private String id;

    public AxInnerServer(String id, int port) {
        this.id = id;
        this.port = port;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(102400, 0, 4, 0, 4)); // 102400 = 100k
                cp.addLast("prepender", HandlerConst.FRAME_PREPENDER);
                cp.addLast("axInboundDecoder", AX_INBOUND_DECODER);
                cp.addLast("pbEncoder", HandlerConst.GPB_ENCODER);
                cp.addLast("axHandler", AX_INNER_HANDLER);
            }
        };
    }
}
