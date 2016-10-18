package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.User;
import org.ogcs.GpbReplys;
import org.ogcs.app.Session;
import org.ogcs.ax.gate.Modules;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.inner.AxReplys;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.gpb.GpbD.Request;
import org.ogcs.gpb.GpbD.Push;
import org.ogcs.gpb.generated.Chat;
import org.ogcs.gpb.generated.Chat.ReqChat;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class CHAT extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        User user = (User) session.getConnector();
        ReqChat reqChat = ReqChat.parseFrom(request.getData());

        if (reqChat.getRid() > 0) {
            AxInnerClient remote = components.getByHash(Modules.MODULE_CHAT, String.valueOf(reqChat.getTarget()));
            if (remote == null) {
                return;
            }
            int method = request.getCmd() + 10000;// TODO: command  = c + 10000
            remote.session().writeAndFlush(
                    AxReplys.axInbound(user.id(), request.getId(), method, request.getData())
            );
        } else { // 全局私聊
            Push build = Push.newBuilder()
                    .setId(100000)
                    .setExtension(Chat.PushChat.pushChat, Chat.PushChat.newBuilder()
                            .setUid(reqChat.getTarget())
                            .setName(reqChat.getName())
                            .setContent(reqChat.getContent())
                            .setTarget(reqChat.getTarget())
                            .build()
                    )
                    .build();
            if (reqChat.getTarget() > 0) {
                // TODO: 根据私聊对象的uid路由 - 获取对方所在的gate   - 相同服务器则直接推送
                AxInnerClient gate = components.getByHash(Modules.MODULE_GATE, String.valueOf(reqChat.getTarget()));
                if (gate == null) {
                    return;
                }
                gate.session().writeAndFlush(
                        AxReplys.axOutbound(request.getId(),
                                GpbReplys.response(request.getId(), build)
                                , reqChat.getTarget())
                );
            } else {
//                components.get()

            }
        }
    }
}
