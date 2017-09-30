package org.okraAx.room.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.common.RoomService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.utilities.ProxyUtil;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.services.ProLoginForRoom;
import org.okraAx.v3.services.ProPlayerRoomCallback;
import org.okraAx.v3.services.ProRoomForLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.09.21
 */
@Service
public final class LoginComponent {

    private static final Logger LOG = LogManager.getLogger(LoginComponent.class);

    private static final LoginForRoomService DEFAULT_LOGIN_SERVICE =
            ProxyUtil.newProxyInstance(LoginForRoomService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[LoginForRoomService] Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });
    @Autowired
    private GpbMessageContext messageContext;
    @Autowired
    private Facade facade;

    private volatile ProxyClient<LoginForRoomService> client;

    /**
     * 初始化
     */
    public void initialize() {
        //  connect to login node.
        ClientContext context = new ClientContext();
        context.initCmdFactory(new GpbCmdFactory(messageContext))
                .registerService(facade, RoomService.class)
                .setAutoConnect(true)
                .setChildThread(1)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("events", new ActiveEventHandler())
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .build();
        messageContext.registerGpbMsgDesc(ProLoginForRoom.getDescriptor());
        messageContext.registerGpbMsgDesc(ProPlayerRoomCallback.getDescriptor());
        messageContext.registerGpbMsgDesc(ProRoomForLogin.getDescriptor());
        //
        context.connect("127.0.0.1", 9007);
    }

    public LoginForRoomService loginClient() {
        return client.impl();
    }

    private class ActiveEventHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

            client = new ProxyClient<>(new NetSession(ctx.channel()), DEFAULT_LOGIN_SERVICE);
            client.initialize();
            //  注册
            client.impl().registerChannel();
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {

            super.channelInactive(ctx);
        }
    }
}