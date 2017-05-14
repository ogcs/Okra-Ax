package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.LogicForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.GpbServerContext;
import org.okraAx.login.component.Facade;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.login.beans.ProLoginBeans;
import org.okraAx.v3.player.beans.ProPlayerBeans;
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
    private GpbServerContext context = AppContext.getBean(GpbServerContext.class);

    public void start() {
        context.registerService(facade, LoginPublicService.class);
        context.registerService(facade, LogicForRoomService.class);
        //  services
        context.registerGpbMsgDesc(ProLogicPublicService.getDescriptor());
        context.registerGpbMsgDesc(ProRoomPublicService.getDescriptor());

        context.registerGpbMsgDesc(ProLoginPublic.getDescriptor());
        context.registerGpbMsgDesc(ProPlayerCallback.getDescriptor());
        //
        context.setCodec(new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())));
        //
        context.start(9005);
        LOG.info("LoginServer bootstrap success.");


    }



}
