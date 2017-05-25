package org.okraAx.login.role.mybatis;

import org.ogcs.app.MultiServiceProxy;
import org.ogcs.app.Session;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 多重代理的连接器. 连接和管理对分布式系统内部连接,进程见Gpc通信
 *
 * @author TinyZ.
 * @since 2.0
 * @version 2017.03.25
 */
public class MultiClient implements MultiServiceProxy {

    private Map<Class<?>, Object> callbacks = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T proxy(Class<T> clz) {
        if (this.callbacks.containsKey(clz)) {
            return (T) this.callbacks.get(clz);
        }
        return null;
    }

    @Override
    public void registerService(Class<?> clz, Session session) {
        Object callback = Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{clz}, new GpbInvocationHandler(session)
        );
        callbacks.put(clz, callback);
    }


}
