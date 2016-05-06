package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbChess.ReqChessJoin;
import org.ogcs.app.Session;
import org.ogcs.ax.component.Modules;
import org.ogcs.ax.component.ServerProperties;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.inner.AxReplys;

public class ROOM_ENTER extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        User user = (User) session.getConnector();
        ReqChessJoin reqChessJoin = ReqChessJoin.parseFrom(request.getData());

        long roomId = reqChessJoin.getRoomId() > 0L ? reqChessJoin.getRoomId() : ServerProperties.id(); //  指定房间进入 或者 创建房间
        AxInnerClient client = components.getByHash(Modules.MODULE_CHESS, String.valueOf(roomId));
        if (client != null) {
            Session coSession = client.session();
            if (coSession != null) {
                coSession.writeAndFlush(
                        AxReplys.axInbound(user.id(), request.getId(), request.getMethod(), request.getData())
                );
            }
        }
    }
}
