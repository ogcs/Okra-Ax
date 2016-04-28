package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.AxComponent;
import com.lj.kernel.ax.core.ConnectorManager;
import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxConnector implements Connector, AxComponent {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private Session session;
    private long id;

    public AxConnector(long id, Session session) {
        this.session = session;
        this.id = id;
    }

    @Override
    public String id() {
        return String.valueOf(id);
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
