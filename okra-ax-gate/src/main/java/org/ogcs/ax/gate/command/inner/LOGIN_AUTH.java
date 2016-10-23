package org.ogcs.ax.gate.command.inner;

import org.ogcs.ax.gate.AuthUtil;
import org.ogcs.gpb.generated.GpbLogin.ReqLoginAuth;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.ax.gpb3.OkraAx.AxInbound;

/**
 * Login服务器授权用户登录
 */
public class LOGIN_AUTH implements Command<Session, AxInbound> {

    @Override
    public void execute(Session session, AxInbound inbound) throws Exception {
        ReqLoginAuth reqLoginAuth = ReqLoginAuth.parseFrom(inbound.getData());
        // 添加授权
        AuthUtil.INSTANCE.put(reqLoginAuth.getUid(), reqLoginAuth.getAuth());
    }
}
