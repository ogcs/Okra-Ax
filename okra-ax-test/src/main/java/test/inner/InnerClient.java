package test.inner;

import com.google.protobuf.ByteString;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.Modules;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.gpb.OkraAx.AxReqAuth;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.AxDatas;

import java.util.List;

/**
 * @author TinyZ on 2016/5/7.
 */
public class InnerClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        List<AxCoInfo> axCoInfos = AxDatas.map.get(String.valueOf(Modules.MODULE_LOGIN));
        AxCoInfo axCoInfo = axCoInfos.get(0);
        axCoInfo.setHost("127.0.0.1");
        axCoInfo.setPort(9005);
        AxInnerClient client = new AxInnerClient("m1", 100, axCoInfo);
        client.start();

        // ..
        ByteString abcd = AxReqAuth.newBuilder()
                .setKey("ABCD")
                .setSource(-1)
                .build().toByteString();
        client.request(-1, 1001, abcd, (t) -> {


            System.out.println();
        });


    }
}
