package org.ogcs.ax.gate.command.impl;

import org.ogcs.ax.gate.AuthUtil;
import org.ogcs.ax.gate.User;
import org.ogcs.ax.gate.command.AgentCommand;
import org.ogcs.ax.gpb3.GpbD.Request;
import org.ogcs.gpb.generated.GpbLogin;
import org.ogcs.gpb.generated.GpbLogin.ReqLoginAuth;
import io.netty.channel.ChannelFutureListener;
import org.ogcs.app.Session;
import org.ogcs.GpbReplys;

import static org.ogcs.ax.config.AxState.STATE_2_AUTH_ERROR;

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
        AuthUtil.INSTANCE.remove(reqLoginAuth.getUid());

        // 游客登录 - 没有用户信息
        User node = new User(session, reqLoginAuth.getUid());
        session.setConnector(node);

        sessions.put(reqLoginAuth.getUid(), node);
        session.writeAndFlush(
                GpbReplys.response(request.getId(), GpbLogin.ResLoginAuth.getDefaultInstance())
        );
    }
}
