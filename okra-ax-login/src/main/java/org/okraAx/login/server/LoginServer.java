package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ConnectionEventHandler;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.login.component.Facade;
import org.okraAx.login.component.UserComponent;
import org.okraAx.utilities.NetHelper;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.beans.player.GpcBnPlayer;
import org.okraAx.v3.services.ProLoginPublic;
import org.okraAx.v3.services.ProPlayerCallback;

/**
 * @author TinyZ.
 * @version 2017.05.06
 */
public final class LoginServer {

    private static final Logger LOG = LogManager.getLogger(LoginServer.class);

    private Facade facade = AppContext.getBean(Facade.class);
    private GpbMessageContext gpbContext = AppContext.getBean(GpbMessageContext.class);

    public void start() {

        ServerContext context = new ServerContext();
        context.initCmdFactory(new GpbCmdFactory(gpbContext))
                .registerService(facade, LoginPublicService.class)
                .registerService(facade, LoginForRoomService.class)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("relay", new RelayHandler())
                .addNetHandler("handler", new GpcEventDispatcher(context, new UserConnectHandler()))
                .build();

        //  message
        gpbContext.registerGpbMsgDesc(ProLoginPublic.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProPlayerCallback.getDescriptor());
        gpbContext.registerGpbMsgDesc(GpcBnPlayer.getDescriptor());
        //
        context.start(9007);
        LOG.info("LoginServer bootstrap success.");


    }

    private class UserConnectHandler implements ConnectionEventHandler {

        private UserComponent userComponent = AppContext.getBean(UserComponent.class);

        @Override
        public void connected() {

        }

        @Override
        public void connectFailed() {

        }

        @Override
        public void disconnected() {
            NetSession session = NetHelper.session();
            if (session != null)
                userComponent.onDisconnect(session);
        }
    }

}
