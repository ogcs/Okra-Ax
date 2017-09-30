package org.okraAx.room.server;

import org.ogcs.app.AppContext;
import org.okraAx.room.component.Facade;
import org.okraAx.room.component.LoginComponent;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ.
 * @version 2017.05.07
 */
public enum Framework {

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
