package org.okraAx.internal.v3;

import org.okraAx.utilities.ProxyUtil;
import org.okraAx.utilities.ReflectionUtil;

import java.lang.reflect.InvocationHandler;

/**
 *
 */
public class ProxyClient<T> {

    /**
     *
     */
    private final NetSession session;
    /**
     * 缺省时的实例
     */
    private final T defaultImpl;
    /**
     * 服务
     */
    private volatile T services;
    /**
     *
     */
    private final InvocationHandler handler;

    public ProxyClient(NetSession session, InvocationHandler handler, T bean) {
        this.session = session;
        this.defaultImpl = bean;
        this.handler = handler;
    }

    public T impl() {
        return services == null ? defaultImpl : services;
    }

    /**
     * @throws IllegalArgumentException 由于defaultImpl参数错误导致的
     */
    public void initialize() {
        if (this.defaultImpl == null)
            throw new NullPointerException("defaultImpl");
        if (this.session == null)
            throw new NullPointerException("session");
        if (this.handler == null)
            throw new NullPointerException("handler");
        try {
            Class<T> clz = ReflectionUtil.tryGetGenericInterface(this.defaultImpl);
            this.services = ProxyUtil.newProxyInstance(clz, this.handler);
        } catch (Exception e) {
            throw new IllegalArgumentException("ProxyClient initialize failed. defaultImpl:"
                    + defaultImpl.getClass(), e);
        }
    }

    public boolean isActive() {
        return session != null && session.isActive();
    }

    public NetSession getSession() {
        return session;
    }

}
