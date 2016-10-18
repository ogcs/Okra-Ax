package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.User;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.gpb.GpbD.Request;
import org.ogcs.app.Session;

/**
 * 获取大厅列表
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class ROOM_HALL extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        User user = (User) session.getConnector();

        // TODO：类似于QQ对战平台 - 显示大厅列表 - 大厅包含多个房间 - 房间包含多个用户

//        AxInnerClient client = components.getByHash(Modules.MODULE_CHESS, String.valueOf(user.getRoomId()));
//        if (client == null) {
//            session.writeAndFlush(GpbReplys.error(request.getId(), -1));
//            return;
//        }
//        client.session().writeAndFlush(
//                AxReplys.axInbound(user.id(), request.getId(), request.getCmd(), request.getData())    //  转发接口为:cmd + 10000
//        );
    }
}
