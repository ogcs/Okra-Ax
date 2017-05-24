package org.okraAx.login.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TinyZ.
 * @version 2017.05.07
 */
public enum Framework {

    INSTANCE;

    private static final String springConfigPath = "classpath:spring/spring.xml";
    private LoginServer loginServer;

    public void start() {
        //  Spring Context
        ClassPathXmlApplicationContext cpxac = new ClassPathXmlApplicationContext(springConfigPath);
        cpxac.registerShutdownHook();
        //  Bootstrap Login Server
        loginServer = new LoginServer();
        loginServer.start();
        //
        PublicServer publicServer = new PublicServer();
        publicServer.start();

    }





}
