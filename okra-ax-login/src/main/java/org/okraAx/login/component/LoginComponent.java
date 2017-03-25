package org.okraAx.login.component;

import com.google.protobuf.Descriptors;
import org.ogcs.app.Session;
import org.okraAx.common.LogicPublicService;
import org.okraAx.internal.v3.AbstractGpbService;
import org.okraAx.utilities.SessionHelper;

/**
 * @author TinyZ.
 * @version 2017.03.25
 */
public class LoginComponent extends AbstractGpbService implements LogicPublicService {


    @Override
    public Descriptors.ServiceDescriptor desc() {
        return null;
    }

    @Override
    public void onLogin(String openId) {

    }

    @Override
    public void onSyncTime() {
        UserClient user = SessionHelper.curPlayer();
        if (user != null && user.isConnected()) {
            user.callback().callbackSyncTime(System.currentTimeMillis());
        }
    }









}
