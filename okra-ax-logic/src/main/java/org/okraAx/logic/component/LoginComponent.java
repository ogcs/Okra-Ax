package org.okraAx.logic.component;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LogicService;
import org.okraAx.common.LoginForLogicService;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
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
 * @version 2017.06.21
 */
@Service
public class LoginComponent {

    private final static Logger LOG = LogManager.getLogger(LoginComponent.class);

    private static final LoginForLogicService EMPTY =
            ProxyUtil.newProxyInstance(LoginForLogicService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[LoginForLogicService]Empty proxy instance invoked by [{}]. args:{}", method.getName(), args);
                return null;
            });

    @Autowired
    private Facade facade;
    @Autowired
    private GpbMessageContext messageContext;

    public volatile ClientContext context;

    public volatile ProxyClient<LoginForLogicService> loginClient;

    /**
     * 初始化
     */
    public synchronized void initialize() {
        context = new ClientContext();
        context.initCmdFactory(new GpbCmdFactory(messageContext))
                .registerService(facade, LogicService.class)
                .setAutoConnect(true)
                .setChildThread(1)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("events", new ActiveEventHandler())
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .build();
        messageContext.registerGpbMsgDesc(ProLoginForRoom.getDescriptor());
        messageContext.registerGpbMsgDesc(ProPlayerRoomCallback.getDescriptor());
        messageContext.registerGpbMsgDesc(ProRoomForLogin.getDescriptor());

        context.connect("127.0.0.1", 9007);
    }

    public LoginForLogicService loginClient() {
        return loginClient.impl();
    }

    public void registerLogin(NetSession session, ConnectionInfo info) {

//        ProxyClient<LoginCallback> client = new ProxyClient<>(session, new GpbInvocationHandler(session), LOGIN_CALL_BACK);
//        client.impl().callbackCreateRole(1);


    }


    private class ActiveEventHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

            NetSession session = new NetSession(ctx.channel());
            loginClient = new ProxyClient<>(session, new GpbInvocationHandler(session), EMPTY);
            loginClient.initialize();
            loginClient.impl().registerLogicServerNode();

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }
    }

}
