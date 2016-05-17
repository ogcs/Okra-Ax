package test;

import com.lj.kernel.gate.client.C2GClient;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.GpbD.Response;
import com.lj.kernel.gpb.generated.GpbLogin;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class UserClient0 {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        long uid = 377317571428353L;
        long auth = 3407410L;
        String host = "192.168.2.29";
        int port = 10001;

        C2GClient client = new C2GClient(uid, host, port) {
            @Override
            public void messageReceived(ChannelHandlerContext ctx, Response msg) {

                System.out.println(msg.toString());
            }
        };
        client.start();
        Channel channel = client.client();

        // 登录
        channel.writeAndFlush(
                Request.newBuilder()
                        .setId(1000)
                        .setCmd(10006)
                        .setData(
                                GpbLogin.ReqLoginAuth.newBuilder()
                                        .setUid(uid)
                                        .setAuth(auth)
                                        .build().toByteString()
                        )
                        .build()
        );
    }
}
