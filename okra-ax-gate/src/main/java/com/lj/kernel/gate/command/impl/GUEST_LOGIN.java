package com.lj.kernel.gate.command.impl;

import com.lj.kernel.gate.AuthUtil;
import com.lj.kernel.gate.User;
import com.lj.kernel.gate.command.AgentCommand;
import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.generated.GpbLogin;
import com.lj.kernel.gpb.generated.GpbLogin.ReqLoginAuth;
import io.netty.channel.ChannelFutureListener;
import org.ogcs.app.Session;
import org.ogcs.ax.component.GpbReplys;

import static org.ogcs.ax.component.AxState.STATE_2_AUTH_ERROR;

/**
 * 登录Gate服务器
 */
public class GUEST_LOGIN extends AgentCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        ReqLoginAuth reqLoginAuth = ReqLoginAuth.parseFrom(request.getData());
        if (!AuthUtil.INSTANCE.verifyAuth(reqLoginAuth.getUid(), reqLoginAuth.getAuth())) {
            session.writeAndFlush(
                    GpbReplys.error(request.getId(), STATE_2_AUTH_ERROR), ChannelFutureListener.CLOSE
            );
            return;
        }
        // 用户登录等
        User node = new User(session, reqLoginAuth.getUid());
        session.setConnector(node);

        sessions.put(reqLoginAuth.getUid(), node);
        session.writeAndFlush(
                GpbReplys.response(request.getId(), GpbLogin.ResLoginAuth.getDefaultInstance())
        );
    }
}
