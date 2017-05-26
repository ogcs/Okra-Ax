package org.okraAx.room.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.ogcs.app.NetSession;
import org.okraAx.common.RoomService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.room.fy.LoginClient;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.services.ProLoginForRoom;
import org.okraAx.v3.services.ProRoomForLogin;
import org.okraAx.v3.services.ProRoomPublic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author TinyZ.
 * @version 2017.05.22
 */
@Service
public class LoginComponent {

    @Autowired
    private GpbMessageContext messageContext;
    
    private LoginClient loginClient;

    @PostConstruct
    public void initialize() {
        //  connect to login node.

        loginClient = new LoginClient();
        ClientContext context = new ClientContext();
        context.initCmdFactory(new GpbCmdFactory(messageContext))
                .registerService(Facade.INSTANCE, RoomService.class)
                .setAutoConnect(true)
                .setChildThread(1)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("events", new ActiveEventHandler())
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .build();
        //
        messageContext.registerGpbMsgDesc(ProLoginForRoom.getDescriptor());
        messageContext.registerGpbMsgDesc(ProRoomPublic.getDescriptor());
        messageContext.registerGpbMsgDesc(ProRoomForLogin.getDescriptor());
        //

        //
        context.connect("127.0.0.1", 9007);
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
