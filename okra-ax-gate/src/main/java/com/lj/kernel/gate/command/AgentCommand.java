package com.lj.kernel.gate.command;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.ax.gate.RemoteManager;
import com.lj.kernel.gpb.generated.GpbD.Request;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public abstract class AgentCommand implements Command<Session, Request> {

    // Service
    protected RemoteManager remoteManager = (RemoteManager) AppContext.getBean(SpringContext.MANAGER_REMOTE);
    protected ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);


}
