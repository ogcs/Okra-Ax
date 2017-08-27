package org.okraAx.internal.v3;

import org.ogcs.app.ServiceProxy;
import org.okraAx.internal.net.NetSession;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ.
 * @version 2017.08.26
 */
public abstract class ProxyUser<I> implements ServiceProxy<I> {

    private NetSession session;
    private I callback;

    public ProxyUser(NetSession session) {
        this.session = session;
    }

    protected static <P> P newProxyInstance(InvocationHandler handler, Class<P> clzOfProxy) {
        return (P) Proxy.newProxyInstance(
                ServiceProxy.class.getClassLoader(),
                new Class[]{clzOfProxy},
                handler);
    }
}
