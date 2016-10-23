package org.ogcs.ax.logic.component;

import org.ogcs.GpbReplys;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.gpb3.GpbD;
import org.ogcs.ax.gpb3.GpbD.Request;
import org.ogcs.ax.gpb3.LogicPublicProto;
import org.ogcs.ax.gpb3.LogicPublicProto.ReqChat;
import org.ogcs.ax.gpb3.LogicPublicProto.ReqLoginAuth;
import org.ogcs.ax.logic.impl.Modules;
import org.ogcs.ax.logic.impl.User;
import org.ogcs.ax.service.AbstractServiceImpl;
import org.ogcs.ax.utilities.AxReplys;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public final class LogicPublicServiceImpl extends AbstractServiceImpl {

    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    private ConnectorManager sessions = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);


    public void onLogin(Session session, Request request, ReqLoginAuth loginAuth) {

//        session.writeAndFlush();
        System.out.println();
    }

    public void onChat(Session session, Request request, ReqChat reqChat) {
        ChatComponent.INSTANCE.onChat(session, request, reqChat);
    }






}
