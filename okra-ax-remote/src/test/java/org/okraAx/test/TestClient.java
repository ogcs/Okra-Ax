package org.okraAx.test;

import io.netty.channel.Channel;
import org.junit.Test;
import org.ogcs.app.AppContext;
import org.okraAx.common.PlayerRoomCallback;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.internal.component.GpbMethodComponent;
import org.okraAx.internal.inner.IrClient;
import org.okraAx.room.fy.impl.FyChessImpl;
import org.okraAx.room.fy.impl.RoomPublicImpl;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.GpcVoid;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author TinyZ.
 * @version 2017.03.17
 */
public class TestClient {

    @Test
    public void test() throws InterruptedException {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        //
        GpbMethodComponent methodComponent = AppContext.getBean(GpbMethodComponent.class);
//        FyRoomServiceImpl roomService = AppContext.getBean(FyRoomServiceImpl.class);
//        FyChessServiceImpl chessService = AppContext.getBean(FyChessServiceImpl.class);

        AtomicBoolean isDone = new AtomicBoolean(false);
        //
        methodComponent.registerMethod(new PlayerRoomCallback() {
            @Override
            public void pong() {
                System.out.println();
                isDone.set(true);
            }
        }, PlayerRoomCallback.class);
        //
        IrClient client = new IrClient("127.0.0.1", 9005);
        client.start();
        Channel channel = client.client();

        channel.writeAndFlush(GpcCall.newBuilder()
                .setMethod("ping")
                .setParams(GpcVoid.getDefaultInstance().toByteString())
                .build());
        while(!isDone.get()) {
            Thread.sleep(1000);
        }
    }

}
