package com.lj.kernel.ax.remote;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class GateNode implements Connector {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private Session session;
    private String gateId;

    public GateNode(Session session, String gateId) {
        this.session = session;
        this.gateId = gateId;
    }

    public String getGateId() {
        return gateId;
    }

    @Override
    public boolean isConnected() {
        return session != null && session.isOnline();
    }

    @Override
    public Session session() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void disconnect() {
        if (session != null) {
            connectorManager.remove(session);

            session.offline();
            session = null;
        }
    }
}
