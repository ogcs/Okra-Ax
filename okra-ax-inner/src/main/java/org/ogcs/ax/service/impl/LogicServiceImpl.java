package org.ogcs.ax.service.impl;

import org.ogcs.app.Session;
import org.ogcs.ax.component.core.AxService;
import org.ogcs.ax.service.AbstractServiceImpl;
import org.ogcs.gpb.ax.LogicPublicService.OnConnectAuth;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public final class LogicServiceImpl extends AbstractServiceImpl {

    public LogicServiceImpl(int id, Class<?> clzOfGpb) {
        super(id, clzOfGpb);
    }

    public void auth(Session session, OnConnectAuth onConnectAuth) {

//        session.writeAndFlush();
        System.out.println();
    }


}
