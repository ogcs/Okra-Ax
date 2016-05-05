/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component.manager;

import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 连接管理器
 * <p>
 * 管理建立的连接
 *
 * @author TinyZ
 * @since 1.0.0
 */
@Service("connectorManager")
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
        if (ids == null){
            throw new NullPointerException("ids");
        }
        for (Object id : ids) {
            Connector connector = connectors.get(id);
            if (connector != null && connector.isConnected())
                connector.session().writeAndFlush(data);
        }
    }

    public void pushById(Object data, List<Object> ids) {
        if (ids == null){
            throw new NullPointerException("ids");
        }
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
