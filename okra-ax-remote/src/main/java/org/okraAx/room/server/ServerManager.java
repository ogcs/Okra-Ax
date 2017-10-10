package org.okraAx.room.server;

import org.ogcs.app.AppContext;
import org.okraAx.room.component.Facade;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * <pre>
 *     1. 初始化各个Component
 *     2. 初始化LoginComponent, 开始注册频道。 连接[login]
 *     3. [login]连接成功的， 像[login]注册节点， 并等待节点注册成功
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.05.07
 */
public enum ServerManager {

    INSTANCE;

    private static final String springConfigPath = "classpath:spring/spring.xml";

    public void start() {
        //  Spring Context
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext(springConfigPath);
        cpxac.registerShutdownHook();
        //  Bootstrap Login Server
        AppContext.getBean(Facade.class).initComponent();

        synchronized (INSTANCE) {
            try {
                INSTANCE.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //
    }


}
