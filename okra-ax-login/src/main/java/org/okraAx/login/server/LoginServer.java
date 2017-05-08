package org.okraAx.login.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterBy41xHandler;
import org.okraAx.common.LogicForRoomService;
import org.okraAx.common.LogicPublicService;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.v3.GpbCommand;
import org.okraAx.internal.v3.GpbServerContext;
import org.okraAx.login.component.Facade;
import org.okraAx.login.component.RoomComponent;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.services.ProLogicPublicService;
import org.okraAx.v3.services.ProRoomPublicService;

/**
 * @author TinyZ.
 * @version 2017.05.06
 */
public final class LoginServer {

    private static final Logger LOG = LogManager.getLogger(RoomComponent.class);

    private Facade facade = AppContext.getBean(Facade.class);

    public void start() {
        final GpbServerContext context = new GpbServerContext();
        //
        context.registerService(facade, LogicPublicService.class);
        context.registerService(facade, LogicForRoomService.class);
        //
        context.registerMsgDesc(ProLogicPublicService.getDescriptor());
        context.registerMsgDesc(ProRoomPublicService.getDescriptor());
        //
        context.setCodec(new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance())));
        //
        context.start(9005);



    }



}
