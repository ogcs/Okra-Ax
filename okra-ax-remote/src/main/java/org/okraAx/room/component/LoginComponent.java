package org.okraAx.room.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.okraAx.common.RoomService;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.room.fy.LoginClient;
import org.okraAx.v3.services.ProRoomService;

/**
 * @author TinyZ.
 * @version 2017.05.22
 */
public class LoginComponent {

    private GpbMessageContext messageContext = AppContext.getBean(GpbMessageContext.class);
    private LoginClient loginClient;

    public void initialize() {
        //  connect to login node.


        ClientContext context = new ClientContext();
        context.registerService(Facade.INSTANCE, RoomService.class)
                .setAutoConnect(true)
                .setChildThread(1)
                .addNetHandler("events", new ActiveEventHandler())
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .build();
        //
        messageContext.registerGpbMsgDesc(ProRoomService.getDescriptor());
        //
        loginClient = new LoginClient(context);
        //
        context.connect("127.0.0.1", 9005);
    }



    private class ActiveEventHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);
            loginClient.setSession(new NetSession(ctx.channel()));
            loginClient.loginClient().registerChannel();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            loginClient.setSession(null);
            super.channelInactive(ctx);
        }
    }



}
