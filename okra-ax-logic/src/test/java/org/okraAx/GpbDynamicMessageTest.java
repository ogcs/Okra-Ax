package org.okraAx;

import org.junit.Test;
import org.ogcs.app.Command;
import org.okraAx.internal.component.GpbServiceComponent;
import org.okraAx.v3.ExamplePublicProto;
import org.okraAx.v3.OkraAx;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ
 * @date 2017-01-16.
 */
public class GpbDynamicMessageTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();
        //
        GpbServiceComponent gpbServiceComponent = cpxac.getBean(GpbServiceComponent.class);
        gpbServiceComponent.registerService(ExamplePublicProto.class);

        OkraAx.AxInbound axInbound = OkraAx.AxInbound.newBuilder()
                .setCmd(12001)
                .setData(
                        ExamplePublicProto.MsgAuth.newBuilder()
                                .setSource(100L)
                                .setKey("Some-Key")
                                .build().toByteString()
                )
                .build();


        Command command = gpbServiceComponent.interpret(12001);
        command.execute(null, axInbound);

    }


}
