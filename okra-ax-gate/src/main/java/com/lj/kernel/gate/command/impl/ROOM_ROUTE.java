package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.GenericCallback;
import com.lj.kernel.gate.Modules;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gate.command.ReplyCodes;
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
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_100_UN_LOGIN));
            return;
        }
        if (user.getRoomId() <= 0) {
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_101_ROOM_IS_NOT_EXIST));
            return;
        }
        String module = Modules.module(reqRoute.getModule());
        AxInnerClient client = components.getByHash(module, String.valueOf(user.getRoomId()));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_102_SERVICE_NOT_EXIST));
            return;
        }
        client.request(user.id(), reqRoute.getCmd(), reqRoute.getData(), new GenericCallback(session, request));
    }
}
