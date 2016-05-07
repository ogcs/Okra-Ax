package test.inner;

import org.ogcs.ax.component.inner.AxInnerServer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ on 2016/5/7.
 */
public class InnerServer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        AxInnerServer server = new AxInnerServer("101", 9005);
        server.start();


    }

}
