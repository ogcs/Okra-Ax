package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.GenericCallback;
import org.ogcs.ax.gate.Modules;
import org.ogcs.ax.gate.User;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.ax.gate.command.ReplyCodes;
import org.ogcs.gpb.GpbD.Request;
import org.ogcs.GpbReplys;
import org.ogcs.gpb.generated.GpbRoom.ReqExit;
import org.ogcs.app.Session;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.gpb.OkraAx;

/**
 * 离开房间
 */
public class ROOM_EXIT extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqExit reqExit = ReqExit.parseFrom(request.getData());
        final User user = (User) session.getConnector();
        if (user == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), ReplyCodes.REPLY_100_UN_LOGIN));
            return;
        }
        if (user.getRoomId() != reqExit.getRoomId()) { // 不同的房间
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        String module = Modules.module(reqExit.getModule());
        AxInnerClient client = components.getByHash(module, String.valueOf(user.getRoomId()));
        if (client == null) {
            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
            return;
        }
        client.request(user.id(), request.getCmd(), request.getData(), new GenericCallback(session, request) {
            @Override
            public void run(OkraAx.AxOutbound msg) {
                super.run(msg);
                if (!msg.hasError()) {// user exit room
                    user.exitRoom();
                }
            }
        });
    }
}
