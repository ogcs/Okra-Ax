package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.GenericCallback;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbRoom.ReqExit;
import org.ogcs.app.Session;
import org.ogcs.ax.component.GpbReplys;
import org.ogcs.ax.component.ServerProperties;
import org.ogcs.ax.component.inner.AxInnerClient;

/**
 * 离开房间
 */
public class ROOM_EXIT extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqExit reqExit = ReqExit.parseFrom(request.getData());
        User user = (User) session.getConnector();
        if (user == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        long roomId = reqExit.getRoomId() > 0L ? reqExit.getRoomId() : ServerProperties.id(); //  指定房间进入 或者 创建房间
        AxInnerClient client = components.getByHash(reqExit.getModule(), String.valueOf(roomId));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        // TODO: 简化请求流程
        client.request(user.id(), request.getCmd(), request.getData(), new GenericCallback(session, request));
    }
}
