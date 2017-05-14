package org.okraAx.internal.v3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Connector;
import org.ogcs.app.ServiceProxy;
import org.ogcs.app.Session;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Login Player.
 *
 * @author TinyZ.
 * @version 2017.02.12
 * @since 2.0
 */
public abstract class ProxyPlayer<P> implements Connector, ServiceProxy<P> {

    private static final Logger LOG = LogManager.getLogger(ProxyPlayer.class);

    protected final Class<P> clzOfProxy;
    protected volatile Session session;
    protected volatile P callback;

    public ProxyPlayer(Session session, Class<P> clzOfProxy) {
        this.session = session;
        this.clzOfProxy = clzOfProxy;
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
        if (this.session != null && session.isActive()) {
            this.sessionActive();
        }
    }

    @Override
    public void sessionActive() {
        this.callback = newProxyInstance(new GpbInvocationHandler(session), clzOfProxy);
    }

    @Override
    public void sessionInactive() {
        if (session != null) {
            session.close();
            this.session = null;
        }
        if (this.callback != null)
            this.callback = null;
    }

    @Override
    public P proxy() {
        return this.callback;
    }

    protected static <P> P newProxyInstance(InvocationHandler handler, Class<P> clzOfProxy) {
        return (P) Proxy.newProxyInstance(
                ProxyPlayer.class.getClassLoader(),
                new Class[]{clzOfProxy},
                handler);
    }
}
