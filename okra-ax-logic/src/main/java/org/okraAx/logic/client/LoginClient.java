package org.okraAx.logic.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.LoginCallback;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * [logic -> login]
 */
public final class LoginClient implements ServiceProxy<LoginCallback> {

    private static final Logger LOG = LogManager.getLogger(LoginClient.class);
    /**
     * The fake session for real logic.
     */
    private static final LoginCallback EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });

    private LoginCallback service;
    private NetSession session;

    public LoginClient() {
    }

    public LoginCallback newOutputProxy() {
        return newProxyInstance(new GpbInvocationHandler(session));
    }

    public LoginCallback loginClient() {
        return proxy() == null ? EMPTY : proxy();
    }

    /**
     * Create new proxy instance for {@link LoginForRoomService}.
     */
    private static LoginCallback newProxyInstance(InvocationHandler handler) {
        return (LoginCallback) Proxy.newProxyInstance(
                LoginClient.class.getClassLoader(),
                new Class[]{LoginCallback.class},
                handler);
    }

    public void setSession(NetSession session) {
        if (session != null && session.isActive()) {
            this.session = session;
            service = newOutputProxy();
        } else {
            this.session = null;
            this.service = null;
        }
    }

    @Override
    public LoginCallback proxy() {
        return service;
    }
}
