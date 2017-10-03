package org.okraAx.internal.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.okraAx.internal.v3.ClientContext;

/**
 * @author TinyZ.
 * @version 2017.10.03
 */
@Sharable
public class AutoConnectHandler extends ChannelInboundHandlerAdapter {

    private ClientContext context;

    public AutoConnectHandler(ClientContext context) {
        this.context = context;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        try {
            super.channelInactive(ctx);
        } finally {
            if (context != null && context.isAutoConnect()) {
                context.doConnect();
            }
        }
    }
}
