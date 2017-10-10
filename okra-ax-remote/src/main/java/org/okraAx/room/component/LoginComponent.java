package org.okraAx.room.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LoginForRemoteService;
import org.okraAx.common.PlayerCallback;
import org.okraAx.common.RemoteService;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.*;
import org.okraAx.utilities.NetHelper;
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

    private static final LoginForRemoteService DEFAULT_LOGIN_SERVICE =
            ProxyUtil.newProxyInstance(LoginForRemoteService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[LoginForRemoteService] Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });

    private static final PlayerCallback DEFAULT_PLAYER_CALL_BACK =
            ProxyUtil.newProxyInstance(PlayerCallback.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[PlayerCallback] Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });

    @Autowired
    private GpbMessageContext messageContext;
    @Autowired
    private Facade facade;

    private volatile ProxyClient<LoginForRemoteService> client;

    private volatile ProxyClient<PlayerCallback> playerClient;

    /**
     * 初始化
     */
    public void initialize() {
        //  connect to login node.
        ClientContext context = new ClientContext();
        context.initCmdFactory(new GpbCmdFactory(messageContext))
                .registerService(facade, RoomPublicService.class)
                .registerService(facade, RemoteService.class)
                .registerService(facade, FyChessService.class)
                .setAutoConnect(true)
                .setChildThread(1)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("handler", new GpcEventDispatcher(context, new WatcherHandler()))
                .build();
        messageContext.registerGpbMsgDesc(ProLoginForRoom.getDescriptor());
        messageContext.registerGpbMsgDesc(ProPlayerRoomCallback.getDescriptor());
        messageContext.registerGpbMsgDesc(ProRoomForLogin.getDescriptor());
        //
        context.connect("127.0.0.1", 9007);
    }

    public LoginForRemoteService loginClient() {
        return client == null ? DEFAULT_LOGIN_SERVICE : client.impl();
    }

    public PlayerCallback playerClient(long uid) {
        if (playerClient.getHandler() instanceof GpbRelayInvocationHandler) {
            ((GpbRelayInvocationHandler) playerClient.getHandler()).setExtraTag(uid);
        }
        return playerClient == null ? DEFAULT_PLAYER_CALL_BACK : playerClient.impl();
    }

    private class WatcherHandler implements org.okraAx.internal.v3.ConnectionEventHandler {

        @Override
        public void connected() {
            NetSession session = NetHelper.session();
            client = GpbProxyUtil.newProxyClient(session, DEFAULT_LOGIN_SERVICE);
            playerClient = GpbProxyUtil.newRelayProxyClient(session, DEFAULT_PLAYER_CALL_BACK);

            //  注册
            client.impl().registerChannel();
        }

        @Override
        public void connectFailed() {

        }

        @Override
        public void disconnected() {
            client = null;
        }
    }
}