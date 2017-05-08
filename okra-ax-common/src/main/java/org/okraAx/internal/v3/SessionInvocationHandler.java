package org.okraAx.internal.v3;

import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy remote produce call implement.
 * using {@link Session#writeAndFlush(Object, ChannelFutureListener)} send params data.
 * @author TinyZ.
 * @version 2017.03.25
 */
public class SessionInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LogManager.getLogger(SessionInvocationHandler.class);
    private final Session session;

    public SessionInvocationHandler(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (this.session != null && this.session.isActive()) {
                GpcCall call = MtdDescUtil.INSTANCE.pack(method, args);
                this.session.writeAndFlush(call);
            }
        } catch (Exception e) {
            LOG.error("[Gpb] InvocationHandler invoke fail. method:" + method.getName(), e);
        }
        return null;
    }
}
