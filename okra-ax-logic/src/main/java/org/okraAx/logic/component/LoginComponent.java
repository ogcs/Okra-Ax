package org.okraAx.logic.component;

import org.ogcs.app.NetSession;
import org.ogcs.app.Session;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.logic.client.LoginClient;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.06.21
 */
@Service
public class LoginComponent {



    public void registerLogin(NetSession session, ConnectionInfo info) {
        LoginClient client = new LoginClient();
        client.setSession(session);

        client.loginClient().callbackCreateRole(1);
    }












}
