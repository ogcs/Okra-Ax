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
package org.ogcs.ax.command;

import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxConnector;
import org.ogcs.ax.config.AxProperties;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.component.manager.ConnectorManager;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import org.ogcs.ax.gpb.OkraAx.AxReqAuth;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/28
 */
public class INNER_AUTH implements Command<Session, AxInbound> {

    private static final Logger LOG = LogManager.getLogger(INNER_AUTH.class);
    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    @Override
    public void execute(Session session, AxInbound axInbound) throws Exception {
        AxReqAuth axReqAuth = AxReqAuth.parseFrom(axInbound.getData());
        if (!axReqAuth.getKey().equals(AxProperties.axInnerAuth)) {
            session.writeAndFlush(AxReplys.error(axInbound.getRid(), -91), ChannelFutureListener.CLOSE);
            return;
        }
        AxConnector axConnector = new AxConnector(axReqAuth.getSource(), session);
        session.setConnector(axConnector);

        connectorManager.put(axConnector.id(), axConnector);

        LOG.info("Inner Auth successful. Node : " + axConnector.id() + ",[" + session.ctx().channel().remoteAddress().toString() + "]");
    }
}
