package org.okraAx.login.component;

import org.ogcs.app.Connector;
import org.ogcs.app.ProxySingleCallback;
import org.ogcs.app.Session;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.v3.AxInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 玩家客户端. 管理session和回调
 * @author TinyZ.
 * @since 2.0
 * @version 2017.03.25
 */
public final class UserClient implements Connector, ProxySingleCallback<PlayerCallback> {

    private final PlayerCallback callback;
    private Session session;

    public UserClient(Session session) {
        this.session = session;
        this.callback = (PlayerCallback) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{PlayerCallback.class}, new AxInvocationHandler(session)
        );
    }

    @Override
    public PlayerCallback callback() {
        return this.callback;
    }

    @Override
    public boolean isConnected() {
        return session != null && session.isOnline();
    }

    @Override
    public Session session() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void disconnect() {
        if (session != null) {
            session.close();
            this.session = null;
        }
    }
}
