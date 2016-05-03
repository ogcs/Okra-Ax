package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD;
import com.lj.kernel.gpb.GpbD.Push;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.Chat;
import com.lj.kernel.gpb.generated.Chat.ReqChat;
import org.ogcs.app.Session;
import org.ogcs.ax.component.GpbReplys;
import org.ogcs.ax.component.Modules;
import org.ogcs.ax.component.inner.AxInnerClient;
import org.ogcs.ax.component.inner.AxReplys;

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
            int method = request.getMethod() + 10000;// TODO: command  = c + 10000
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