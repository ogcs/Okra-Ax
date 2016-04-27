package com.lj.kernel.ax.client;

import com.lj.kernel.ax.Modules;
import com.lj.kernel.ax.core.GpbClient;
import com.lj.kernel.gpb.generated.Gate.ReqGateAuth;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbD.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;

import static com.lj.kernel.ax.HandlerConst.GPB_RESPONSE_DECODER;

/**
 * User client connect to Gate
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class C2GClient extends GpbClient<Response> {

    private long uid;

    public C2GClient(long uid, String host, int port) {
        super(host, port, true);
        this.uid = uid;
    }

    @Override
    public void addGpbDecoder(ChannelPipeline cp) {
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
        session.writeAndFlush(Request.newBuilder()
                .setId(REQUEST_ID.getAndIncrement())
                .setApi(Modules.MODULE_GATE)
                .setMethod(10001)
                .setData(build.toByteString())
                .build());
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, Response msg) {

    }
}
