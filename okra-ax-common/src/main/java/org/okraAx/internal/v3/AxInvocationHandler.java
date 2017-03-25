package org.okraAx.internal.v3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author TinyZ.
 * @version 2017.03.25
 */
public class AxInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LogManager.getLogger(AxInvocationHandler.class);
    private final Session session;

    public AxInvocationHandler(Session session) {
        this.session = session;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            GpcCall call = MtdDescUtil.INSTANCE.pack(method, args);
            if (this.session != null && this.session.isOnline()) {
                this.session.writeAndFlush(call);
            }
        } catch (Exception e) {
            LOG.error("[Gpb] InvocationHandler invoke fail. method:" + method.getName(), e);
        }
        return null;
    }
}
