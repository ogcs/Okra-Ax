package org.okraAx.axrpc;

import org.junit.Test;
import org.ogcs.app.AppContext;
import org.okraAx.internal.inner.axrpc.IrServer;
import org.okraAx.internal.component.GpbServiceComponent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ
 * @date 2017-02-08.
 */
public class MainTest {

    @Test
    public void test() throws Exception {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();
        //

        GpbServiceComponent serviceManager = AppContext.getBean(GpbServiceComponent.class);
//        serviceManager.registerService(IrcProto.class);
//        serviceManager.registerService(IrcsProto.class);

        IrServer server = new IrServer("test-logic-1", 9005);
        server.start();


    }

    public void testClient() {


    }

}
