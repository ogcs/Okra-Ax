package com.lj.kernel.gate.command.impl;

import com.lj.kernel.ax.Modules;
import com.lj.kernel.ax.AxReplys;
import com.lj.kernel.ax.gate.G2RClient;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.generated.Chat;
import com.lj.kernel.gpb.generated.Chat.ReqChat;
import com.lj.kernel.gpb.generated.GpbD;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.GpbD.Request;
import io.netty.channel.Channel;
import org.ogcs.app.Session;

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
            G2RClient remote = remoteManager.get(Modules.MODULE_CHAT, String.valueOf(reqChat.getTarget()));
            if (remote == null) {
                return;
            }
            remote.session().writeAndFlush(
                    Inbound.newBuilder()
                            .setUid(user.id())
                            .setId(request.getId())
                            .setMethod(request.getMethod() + 10000)  // TODO: command  = c + 10000
                            .setData(request.getData())
                            .build()
            );
        } else { // 全局私聊
            if (reqChat.getTarget() > 0) {
                // TODO: 根据私聊对象的uid路由 - 获取对方所在的gate
                G2RClient gate = remoteManager.get(Modules.MODULE_GATE, String.valueOf(reqChat.getTarget()));
                if (gate == null) {
                    return;
                }
                Channel channel = gate.client(); // TODO: 发送消息保护
                if (channel.isWritable()) {
                    GpbD.Push build = GpbD.Push.newBuilder()
                            .setId(100000)
                            .setExtension(Chat.PushChat.pushChat, Chat.PushChat.newBuilder()
                                    .setUid(reqChat.getTarget())
                                    .setName(reqChat.getName())
                                    .setContent(reqChat.getContent())
                                    .setTarget(reqChat.getTarget())
                                    .build()
                            )
                            .build();
                    channel.writeAndFlush(AxReplys.outbound(AxReplys.response(-1, build.toByteString()), reqChat.getTarget()));
                }
            } else {

            }
        }

//        G2RClient g2RClient1 = remoteManager.get(String.valueOf(request.getApi()), request.getKey());
//        if (g2RClient1 == null) {
//
//            return;
//        }
//        Channel channel = g2RClient1.client(); // TODO: 发送消息保护
//        if (channel.isWritable()) {
//            channel.writeAndFlush(
//                    Unpooled.wrappedBuffer(request.getData().toByteArray())
//            );
//        }
    }
}
