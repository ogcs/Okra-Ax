package org.ogcs.ax.gate.command;

import org.ogcs.gpb.GpbD.Request;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.component.service.AxInnerCoManager;
import org.ogcs.ax.component.service.ConnectorManager;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public abstract class AgentCommand implements Command<Session, Request> {

    // Service
    protected AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    protected ConnectorManager sessions = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);


}
