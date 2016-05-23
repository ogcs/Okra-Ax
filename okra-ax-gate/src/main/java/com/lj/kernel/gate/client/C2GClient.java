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

package com.lj.kernel.gate.client;

import com.lj.kernel.gpb.GpbD;
import com.lj.kernel.gpb.GpbD.Response;
import com.lj.kernel.gpb.generated.Gate.ReqGateAuth;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.ogcs.ax.component.inner.GpbClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;



/**
 * User client connect to Gate
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class C2GClient extends GpbClient<Response> {

    private static final ProtobufDecoder GPB_RESPONSE_DECODER = new ProtobufDecoder(Response.getDefaultInstance());
    private long uid;

    public C2GClient(long uid, String host, int port) {
        super(host, port, true);
        this.uid = uid;
    }

    @Override
    public void addCodec(ChannelPipeline cp) {
        cp.addLast("gpbDecoder", GPB_RESPONSE_DECODER);
    }

    @Override
    public void connectionActive(ChannelHandlerContext ctx) {
        super.connectionActive(ctx);
// 连接到gate - 注册
        ReqGateAuth build = ReqGateAuth.newBuilder()
                .setAuth("ABCD")
                .setId(uid)
                .build();
        session.writeAndFlush(GpbD.Request.newBuilder()
                .setId(REQUEST_ID.getAndIncrement())
                .setCmd(10001)
                .setData(build.toByteString())
                .build());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Response msg) {

    }
}
