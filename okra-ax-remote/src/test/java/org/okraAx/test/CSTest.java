package org.okraAx.test;

import io.netty.channel.Channel;
import org.junit.Test;
import org.ogcs.app.AppContext;
import org.okraAx.internal.v3.GpbMethodComponent;
import org.okraAx.internal.inner.axrpc.IrClient;
import org.okraAx.internal.inner.axrpc.IrServer;
import org.okraAx.internal.v3.interfaces.FyChessService;
import org.okraAx.internal.v3.interfaces.FyRoomService;
import org.okraAx.room.fy.impl.FyChessServiceImpl;
import org.okraAx.room.fy.impl.FyRoomServiceImpl;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.GpcVoid;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ
 * @date 2017-03-01.
 */
public class CSTest {

    @Test
    public void test1() {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        //
        GpbMethodComponent methodComponent = AppContext.getBean(GpbMethodComponent.class);
//        FyRoomServiceImpl roomService = AppContext.getBean(FyRoomServiceImpl.class);
//        FyChessServiceImpl chessService = AppContext.getBean(FyChessServiceImpl.class);

        //
        methodComponent.registerMethod(new FyRoomServiceImpl(), FyRoomService.class);
        methodComponent.registerMethod(new FyChessServiceImpl(), FyChessService.class);
        //
        IrServer server = new IrServer("1", 9005);
        server.start();

        IrClient client = new IrClient("127.0.0.1", 9005);
        client.start();
        Channel channel = client.client();

        //
        channel.writeAndFlush(GpcCall.newBuilder()
                .setMethod("ping")
                .setParams(GpcVoid.getDefaultInstance().toByteString())
                .build());

        while(true) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
