package org.okraAx.internal.v3;

import io.netty.channel.Channel;
import org.ogcs.app.NetSession;
import org.ogcs.app.ServiceProxy;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;

import java.lang.reflect.Proxy;

/**
 * 装饰{@link org.ogcs.app.Session}类，增加代理模式， 以简化接口访问的通信逻辑.
 *
 * @author TinyZ
 * @version 2017.02.17.
 * @since 2.0
 */
public class ProxySession<I> extends NetSession implements ServiceProxy<I> {

    /**
     * The proxy class instance for the specified interfaces.
     */
    private final I invoker;

    public ProxySession(Channel channel, Class<I> clz) {
        super(channel);
        this.invoker = (I) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{clz}, new GpbInvocationHandler(this)
        );
    }

    /**
     * @return The proxy class instance for the specified interfaces.
     */
    @Override
    public I proxy() {
        return invoker;
    }
}
