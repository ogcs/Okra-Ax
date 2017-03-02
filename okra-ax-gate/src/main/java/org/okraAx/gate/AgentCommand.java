package org.okraAx.gate;

import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.component.manager.AxInnerCoManager;
import org.okraAx.component.manager.ConnectorManager;
import org.okraAx.internal.SpringContext;
import org.okraAx.v3.GpbD;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public abstract class AgentCommand implements Command<Session, GpbD.Request> {

    // Service
    protected AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);
    protected ConnectorManager sessions = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);


}
