package org.okraAx.login.component;

import org.ogcs.app.Session;
import org.okraAx.login.mybatis.UserClient;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Component;

/**
 * @author TinyZ.
 * @version 2017.03.25
 */
@Component
public class LoginComponent {


    public void onLogin(String openId) {
        Session session = SessionHelper.currentSession();
        //  TODO: 授权验证

        UserClient userClient = new UserClient(session);
        userClient.invoker().callbackLogin();
    }

    public void onSyncTime() {
        UserClient user = SessionHelper.curPlayer();
        if (user != null && user.isOnline()) {
            user.invoker().callbackSyncTime(System.currentTimeMillis());
        }
    }

    public void onShowChannelList() {

    }

    public void onEnterChannel() {

    }


}
