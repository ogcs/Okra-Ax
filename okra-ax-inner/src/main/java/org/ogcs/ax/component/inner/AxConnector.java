/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.ogcs.ax.component.inner;

import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.ogcs.ax.component.core.AxComponent;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.component.manager.ConnectorManager;

/**
 * Ax connector.
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class AxConnector implements Connector, AxComponent {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);
    private volatile Session session;
    private final long id;

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
