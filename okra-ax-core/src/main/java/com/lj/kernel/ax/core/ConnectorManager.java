package com.lj.kernel.ax.core;

import com.lj.kernel.ax.SpringContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * 连接管理器
 *
 * 管理建立的连接
 *
 * @author TinyZ
 * @since 1.0.0
 */
@Service(SpringContext.MANAGER_CONNECTOR)
public class ConnectorManager {

    // id - connector
    private Map<Object, Connector> connectors = new ConcurrentHashMap<>();

    //
    private Map<Session, Object> sessions = new ConcurrentHashMap<>();

    public void put(Object id, Connector connector) {
        connectors.put(id, connector);
        sessions.put(connector.session(), id);
    }

    public Connector get(Object id) {
        return connectors.get(id);
    }

    public Set<Connector> getAll() {
        return connectors.values().stream().collect(Collectors.toSet());
    }

    public void remove(Session session) {
        Object id = sessions.remove(session);
        if (id != null) {
            connectors.remove(id);
        }
    }

    public void pushById(Object data, Object... ids) {
        for (Object id : ids) {
            Connector connector = connectors.get(id);
            if (connector != null && connector.isConnected())
                connector.session().writeAndFlush(data);
        }
    }

    public void pushById(Object data, List<Object> ids) {
        for (Object id : ids) {
            Connector connector = connectors.get(id);
            if (connector != null && connector.isConnected())
                connector.session().writeAndFlush(data);
        }
    }

    public void pushAll(Object data) {
        connectors.forEach((obj, connector) -> {
            if (connector.isConnected())
                connector.session().writeAndFlush(data);
        });
    }
}
