package org.okraAx.internal.v3.protobuf;

import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Session;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Proxy remote produce call implement.
 * using {@link Session#writeAndFlush(Object, ChannelFutureListener)} send params data.
 *
 * @author TinyZ.
 * @version 2017.03.25
 * @see ServerContext
 */
public class GpbInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LogManager.getLogger(GpbInvocationHandler.class);
    private GpbMessageContext context = AppContext.getBean(GpbMessageContext.class);
    private final Session session;

    public GpbInvocationHandler(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (this.context == null || this.session == null || !this.session.isActive()) {
                LOG.error("[Gpb] context error. method:" + method.getName());
                return null;
            }
            GpcCall call = this.context.pack(method, args);
            this.session.writeAndFlush(call);
        } catch (Exception e) {
            LOG.error("[Gpb] method[" + method.getName() + "] invoke exception. ", e);
        }
        return null;
    }
}
