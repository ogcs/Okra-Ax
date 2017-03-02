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

package org.okraAx.logic.client;

import org.okraAx.utilities.GpbReplys;
import org.okraAx.v3.GpbD;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.okraAx.internal.inner.GpbClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import org.okraAx.v3.LogicPublicProto.ReqLoginAuth;


/**
 * User client connect to Gate
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class C2GClient extends GpbClient<GpbD.Response> {

    private static final ProtobufDecoder GPB_RESPONSE_DECODER = new ProtobufDecoder(GpbD.Response.getDefaultInstance());
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
        ReqLoginAuth build = ReqLoginAuth.newBuilder()
                .setUid(uid)
                .setAuth(9587)
                .build();
        session.writeAndFlush(GpbD.Request.newBuilder()
                .setId(GpbClient.REQUEST_ID.getAndIncrement())
                .setData(GpbReplys.any(10001, build))
                .build());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, GpbD.Response msg) {

    }
}
