package com.lj.kernel.ax;

import com.lj.kernel.gpb.OkraAx;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.gpb.OkraAx.AxOutbound;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.GpbD.Outbound;
import com.lj.kernel.gpb.generated.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbD.Response;
import io.netty.channel.ChannelHandler;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public interface HandlerConst {

    ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);

    ChannelHandler GPB_ENCODER = new ProtobufEncoder();



    ProtobufDecoder GPB_REQUEST_DECODER = new ProtobufDecoder(Request.getDefaultInstance());
    ProtobufDecoder GPB_RESPONSE_DECODER = new ProtobufDecoder(Response.getDefaultInstance());

    ProtobufDecoder GPB_INBOUND_DECODER = new ProtobufDecoder(Inbound.getDefaultInstance());
    ProtobufDecoder GPB_OUTBOUND_DECODER = new ProtobufDecoder(Outbound.getDefaultInstance());

    ProtobufDecoder AX_INBOUND_DECODER = new ProtobufDecoder(AxInbound.getDefaultInstance());
    ProtobufDecoder AX_OUTBOUND_DECODER = new ProtobufDecoder(AxOutbound.getDefaultInstance());
}
