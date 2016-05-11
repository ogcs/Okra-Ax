package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.GenericCallback;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbRoom.ReqRoute;
import org.ogcs.app.Session;
import org.ogcs.ax.component.GpbReplys;
import org.ogcs.ax.component.inner.AxInnerClient;

/**
 * 路由房间相关的接口
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class ROOM_ROUTE extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqRoute reqRoute = ReqRoute.parseFrom(request.getData());
        User user = (User) session.getConnector();
        if (user == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        AxInnerClient client = components.getByHash(reqRoute.getModule(), String.valueOf(user.getRoomId()));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        client.request(user.id(), reqRoute.getCmd(), reqRoute.getData(), new GenericCallback(session, request));
    }
}
