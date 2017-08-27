package org.okraAx.login.component;

import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.okraAx.utilities.AxAnyUtil;
import org.okraAx.v3.AxAnyProto;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.GpcRelay;

/**
 * 中继转发handler
 * @author TinyZ.
 * @version 2017.03.29
 */
public class RelayHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        if (msg instanceof GpcRelay) {
//            AxAnyProto.AxAny source = ((GpcRelay) msg).getSource();
//            Message unpack = AxAnyUtil.unpack();
//
//        }
        super.channelRead(ctx, msg);
    }
}
