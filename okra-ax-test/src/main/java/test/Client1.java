package test;

import org.ogcs.ax.gate.client.C2GClient;
import org.ogcs.gpb.GpbD.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class Client1 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        long uid = 101;
        C2GClient client = new C2GClient(uid, "127.0.0.1", 10001) {
            @Override
            public void messageReceived(ChannelHandlerContext ctx, Response msg) {

                System.out.println(msg.toString());
            }
        };
        client.start();
        Channel channel = client.client();

// Send Message
//        channel.writeAndFlush(Gpb.Request.newBuilder()
//                .setId(1000)
//                .build());

    }
}
