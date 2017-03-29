package org.okraAx.login.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author TinyZ.
 * @version 2017.03.29
 */
public class RelayHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        super.channelRead(ctx, msg);
    }
}
