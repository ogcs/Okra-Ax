package org.okraAx.utilities;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ.
 * @version 2017.09.15
 */
public class ProxyUtil {

    /**
     * Create new proxy instance.
     *
     * @throws IllegalArgumentException if {@code clz} does not specify the type of a Java interface
     */
    public static <T> T newProxyInstance(Class<T> clz, InvocationHandler handler) {
        if (clz == null)
            throw new NullPointerException("clz");
        if (!clz.isInterface())
            throw new IllegalArgumentException(String.format("clz %s is not an interface", clz));
        Object obj = Proxy.newProxyInstance(
                clz.getClassLoader(),
                new Class[]{clz},
                handler);
        return clz.cast(obj);
    }

    private ProxyUtil() {
        //  no-op
    }

}
