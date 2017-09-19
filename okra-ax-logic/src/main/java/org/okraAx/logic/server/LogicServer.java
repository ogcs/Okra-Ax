package org.okraAx.logic.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.LogicPublicService;
import org.okraAx.common.LogicService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.internal.v3.protobuf.GpbCmdFactory;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.internal.v3.protobuf.GpcEventDispatcher;
import org.okraAx.logic.component.Facade;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.services.ProLoginPublic;
import org.okraAx.v3.services.ProPlayerCallback;
import org.okraAx.v3.services.ProRoomForLogin;

/**
 * @author TinyZ.
 * @version 2017.06.21
 */
public class LogicServer {

    private static final Logger LOG = LogManager.getLogger(LogicServer.class);

    private Facade facade = AppContext.getBean(Facade.class);
    private GpbMessageContext gpbContext = AppContext.getBean(GpbMessageContext.class);

    public void start() {
        ServerContext context = new ServerContext();
        context.initCmdFactory(new GpbCmdFactory(gpbContext))
                .registerService(facade, LogicPublicService.class)
                .registerService(facade, LogicService.class)
                .addNetHandler("codec", new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())))
                .addNetHandler("handler", new GpcEventDispatcher(context))
                .build();

        //  message
        gpbContext.registerGpbMsgDesc(ProRoomForLogin.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProLoginPublic.getDescriptor());
        gpbContext.registerGpbMsgDesc(ProPlayerCallback.getDescriptor());
        //
        context.start(9007);
        LOG.info("LoginServer bootstrap success.");


    }

}
