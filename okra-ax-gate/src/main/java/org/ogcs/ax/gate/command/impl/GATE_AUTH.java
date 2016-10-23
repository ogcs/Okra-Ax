package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.User;
import org.ogcs.GpbReplys;
import io.netty.channel.ChannelFutureListener;
import org.ogcs.app.Session;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.ax.gpb3.GpbD.Request;
import org.ogcs.gpb.generated.Gate.ReqGateAuth;

import static org.ogcs.ax.config.AxState.STATE_2_AUTH_ERROR;


/**
 * API: 101
 *
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/22
 */
public class GATE_AUTH extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqGateAuth reqGateAuth = ReqGateAuth.parseFrom(request.getData());
        if (!reqGateAuth.getAuth().equals("ABCD")) {
            session.writeAndFlush(GpbReplys.error(request.getId(), STATE_2_AUTH_ERROR), ChannelFutureListener.CLOSE);
            return;
        }
        // 用户登录等
        User node = new User(session, reqGateAuth.getId());
        session.setConnector(node);

        sessions.put(reqGateAuth.getId(), node);


        // Gpb.Response 返回用户登录成功信息
    }
}
