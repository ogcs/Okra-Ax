package org.okraAx.logic;

import org.okraAx.internal.InnerFramework;
import org.okraAx.internal.config.AxProperties;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext("classpath:spring/spring.xml");
        cpxac.registerShutdownHook();

        InnerFramework framework = new InnerFramework();
        framework.start();

        String id = String.valueOf(AxProperties.axId);
        // gate
        if (AxProperties.axBind > 0) {
//            AxGate gate = new AxGate(id, AxProperties.axBind);
//            gate.start();
        } else if (AxProperties.axLoginPort > 0) {
            // 启动外部服务器
//            HttpServer login = new HttpServer(AxProperties.axLoginPort);
//            login.start();
        }
    }
}
