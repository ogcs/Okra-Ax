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


import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.ax.component.bean.AxCoInfo;
import org.ogcs.ax.config.SpringContext;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.utilities.AxReplys;
import org.ogcs.ax.component.manager.AxInnerCoManager;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import org.ogcs.ax.gpb.OkraAx.AxNodeInfo;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/28
 */
public class INNER_ADD_CO implements Command<Session, AxInbound> {

    private AxInnerCoManager components = (AxInnerCoManager) AppContext.getBean(SpringContext.MANAGER_AX_COMPONENT);

    @Override
    public void execute(Session session, AxInbound axInbound) throws Exception {
        AxNodeInfo nodeInfo = AxNodeInfo.parseFrom(axInbound.getData());

        // 更新组件
        AxCoInfo info = new AxCoInfo(nodeInfo.getId(), nodeInfo.getHost(), nodeInfo.getPort(), nodeInfo.getBind());

        AxInnerClient client = new AxInnerClient(String.valueOf(nodeInfo.getModule()), 1L, info);
        client.start();

        if (client.session().isOnline()) {
            // 成功建立连接。

        }


        // TODO: 如何确认已经简历连接
        session.writeAndFlush(AxReplys.axOutbound(axInbound.getRid(), axInbound.getData(), axInbound.getSource()));
        System.out.println("节点注册成功.");







    }
}
