package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.Gate.ReqGateAuth;
import io.netty.channel.ChannelFutureListener;
import org.ogcs.app.Session;
import org.ogcs.ax.component.GpbReplys;

import static org.ogcs.ax.component.AxState.STATE_2_AUTH_ERROR;


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
