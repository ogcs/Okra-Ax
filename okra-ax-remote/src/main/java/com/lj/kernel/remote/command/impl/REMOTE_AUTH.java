package com.lj.kernel.remote.command.impl;

import com.lj.kernel.ax.SpringContext;
import com.lj.kernel.ax.core.ConnectorManager;
import com.lj.kernel.ax.remote.GateNode;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.Remote.ReqRemoteAuth;
import com.lj.kernel.remote.command.RemoteCommand;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;

/**
 * API: 101
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/22
 */
public class REMOTE_AUTH extends RemoteCommand {

    private ConnectorManager connectorManager = (ConnectorManager) AppContext.getBean(SpringContext.MANAGER_CONNECTOR);

    @Override
    public void execute(Session session, Inbound inbound) throws Exception {
        ReqRemoteAuth reqRemoteAuth = ReqRemoteAuth.parseFrom(inbound.getData());
        if (!reqRemoteAuth.getAuth().equals("ABCD")) {
            session.ctx().close();// 访问授权校验失败 - 断开连接
            return;
        }
        GateNode node = new GateNode(session, reqRemoteAuth.getId());
        session.setConnector(node);

        connectorManager.put(reqRemoteAuth.getId(), node);
        System.out.println(reqRemoteAuth.getId());
    }
}
