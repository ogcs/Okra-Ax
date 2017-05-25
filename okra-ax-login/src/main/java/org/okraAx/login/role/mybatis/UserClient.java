package org.okraAx.login.role.mybatis;

import org.ogcs.app.Connector;
import org.ogcs.app.ServiceProxy;
import org.ogcs.app.Session;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 玩家客户端. 管理session和回调
 * @author TinyZ.
 * @since 2.0
 * @version 2017.03.25
 */
public final class UserClient implements Connector, ServiceProxy<PlayerCallback> {

    private final PlayerCallback callback;
    private volatile Session session;

    public UserClient(Session session) {
        this.session = session;
        this.callback = (PlayerCallback) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{PlayerCallback.class}, new GpbInvocationHandler(session)
        );
    }

    @Override
    public PlayerCallback proxy() {
        return this.callback;
    }

    @Override
    public boolean isOnline() {
        return session != null && session.isActive();
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
    public void sessionActive() {

    }

    @Override
    public void sessionInactive() {
        if (session != null) {
            session.close();
            this.session = null;
        }
    }
}
