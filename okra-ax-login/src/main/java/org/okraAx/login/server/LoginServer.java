package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.LogicForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.login.component.Facade;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.services.ProLogicPublicService;
import org.okraAx.v3.services.ProLoginPublic;
import org.okraAx.v3.services.ProPlayerCallback;
import org.okraAx.v3.services.ProRoomPublicService;

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
        context.registerService(facade, LoginPublicService.class)
                .registerService(facade, LogicForRoomService.class)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .initCmdFactory(new GpbCmdFactory(gpbContext))
                .build();

        //  message
        gpbContext.registerGpbMsgDesc(ProLogicPublicService.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProRoomPublicService.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProLoginPublic.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProPlayerCallback.getDescriptor());
        //
        context.start(9005);
        LOG.info("LoginServer bootstrap success.");


    }


}
