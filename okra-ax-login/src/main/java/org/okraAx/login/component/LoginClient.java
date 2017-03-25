package org.okraAx.login.component;

import org.ogcs.app.Connector;
import org.ogcs.app.ProxyMultiCallback;
import org.ogcs.app.Session;
import org.okraAx.internal.v3.AxInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.03.25
 */
public class LoginClient implements Connector, ProxyMultiCallback {

    private Map<Class<?>, Object> callbacks = new ConcurrentHashMap<>();
    private final Session session;

    public LoginClient(Session session) {
        this.session = session;
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public Session session() {
        return null;
    }

    @Override
    public void setSession(Session session) {

    }

    @Override
    public void disconnect() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T callback(Class<T> clz) {
        if (this.callbacks.containsKey(clz)) {
            return (T)this.callbacks.get(clz);
        }
        return null;
    }

    @Override
    public void registerCallback(Class<?> clz, Session session) {
        Object callback = Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{clz}, new AxInvocationHandler(session)
        );
        callbacks.put(clz, callback);
    }










}
