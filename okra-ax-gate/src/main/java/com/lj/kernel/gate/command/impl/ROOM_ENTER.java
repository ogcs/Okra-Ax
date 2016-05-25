package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.GenericCallback;
import com.lj.kernel.gate.Modules;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gate.command.ReplyCodes;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.GpbReplys;
import com.lj.kernel.gpb.generated.GpbRoom.ReqEnter;
import org.ogcs.app.Session;
import org.ogcs.ax.component.ServerProperties;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.gpb.OkraAx;
import org.ogcs.ax.gpb.OkraAx.AxOutbound;

/**
 * 进入房间
 */
public class ROOM_ENTER extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqEnter reqEnter = ReqEnter.parseFrom(request.getData());
        final User user = (User) session.getConnector();
        if (user == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_100_UN_LOGIN));
            return;
        }
        String module = Modules.module(reqEnter.getModule());
        long roomId = reqEnter.getRoomId() > 0L ? reqEnter.getRoomId() : ServerProperties.id(); //  指定房间进入 或者 创建房间
        AxInnerClient client = components.getByHash(module, String.valueOf(roomId));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_102_SERVICE_NOT_EXIST));
            return;
        }
        client.request(user.id(), request.getCmd(), request.getData(), new GenericCallback(session, request) {
            @Override
            public void run(AxOutbound msg) {
                super.run(msg);
                if (!msg.hasError()) {// user enter room.
                    user.enterRoom(module, roomId);
                }
            }
        });
    }
}
