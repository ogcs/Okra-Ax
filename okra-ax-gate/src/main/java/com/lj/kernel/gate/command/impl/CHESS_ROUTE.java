package com.lj.kernel.gate.command.impl;

import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.ax.Modules;
import com.lj.kernel.ax.inner.AxInnerClient;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.generated.GpbD.Request;
import org.ogcs.app.Session;

/**
 * 象棋路由
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_ROUTE extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        User user = (User) session.getConnector();
        AxInnerClient client = components.getByHash(Modules.MODULE_CHESS, String.valueOf(user.getRid()));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        client.session().writeAndFlush(
                GpbReplys.inbound(user.id(), request.getId(), request.getMethod() + 10000, request.getData())    //  转发接口为:cmd + 10000
        );
    }
}
