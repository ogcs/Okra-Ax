package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.GenericCallback;
import com.lj.kernel.gate.Modules;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.GpbReplys;
import com.lj.kernel.gpb.generated.GpbRoom.ReqEnter;
import org.ogcs.app.Session;
import org.ogcs.ax.component.ServerProperties;
import org.ogcs.ax.component.inner.AxInnerClient;

/**
 * 进入房间
 */
public class ROOM_ENTER extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqEnter reqEnter = ReqEnter.parseFrom(request.getData());
        User user = (User) session.getConnector();
        if (user == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        String module = Modules.module(reqEnter.getModule());
        long roomId = reqEnter.getRoomId() > 0L ? reqEnter.getRoomId() : ServerProperties.id(); //  指定房间进入 或者 创建房间
        AxInnerClient client = components.getByHash(module, String.valueOf(roomId));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        client.request(user.id(), request.getCmd(), request.getData(), new GenericCallback(session, request));
    }
}
