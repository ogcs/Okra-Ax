package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.GenericCallback;
import org.ogcs.ax.gate.Modules;
import org.ogcs.ax.gate.User;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.ax.gate.command.ReplyCodes;
import org.ogcs.gpb.GpbD.Request;
import org.ogcs.GpbReplys;
import org.ogcs.gpb.generated.GpbRoom.ReqEnter;
import org.ogcs.app.Session;
import org.ogcs.ax.config.ServerProperties;
import org.ogcs.ax.component.inner.AxInnerClient;
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
