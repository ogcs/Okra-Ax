package org.okraAx.login.server;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.ogcs.app.AppContext;
import org.okraAx.login.component.UserComponent;
import org.okraAx.v3.GpcRelay;

/**
 * Simple Message Relay Handler
 *
 * @author TinyZ.
 * @version 2017.03.29
 */
@Sharable
public class RelayHandler extends ChannelInboundHandlerAdapter {

    private UserComponent userComponent = AppContext.getBean(UserComponent.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof GpcRelay) {
            User user = userComponent.getUserByUid(((GpcRelay) msg).getSource());
            if (user != null && user.session() != null && user.session().isActive()) {
                user.session().writeAndFlush(((GpcRelay) msg).getData());
            }
            return;
        }
        super.channelRead(ctx, msg);
    }
}
