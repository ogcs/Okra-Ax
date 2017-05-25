package org.okraAx.internal.v3.protobuf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.Method;

/**
 * <pre>通过AxAny的实现动态解析message</pre>
 *
 * @author TinyZ
 * @see com.google.protobuf.Any
 */
public class GpbCommand implements Command<Session, GpcCall> {

    private static final Logger LOG = LogManager.getLogger(GpbCommand.class);
    private final Object instance;
    private final Method methodImpl;
    private GpbMessageContext context;

    public GpbCommand(Object instance, Method methodImpl, GpbMessageDesc methodDesc) {
        this.instance = instance;
        this.methodImpl = methodImpl;
    }

    public GpbCommand(Object instance, Method methodImpl, GpbMessageContext context) {
        this.instance = instance;
        this.methodImpl = methodImpl;
        this.context = context;
    }

    @Override
    public void execute(Session session, GpcCall call) throws Exception {
        if (instance == null) throw new NullPointerException("instance");
        if (methodImpl == null) throw new NullPointerException("methodImpl");
        if (context == null) throw new NullPointerException("context");
        try {
            SessionHelper.setSession(session);
            methodImpl.invoke(instance, context.unpack(call));
        } catch (Exception e) {
            LOG.error("[Gpb] RPC invoke error. method :" + call.getMethod() + ", call:" + call.toString(), e);
        } finally {
            SessionHelper.setSession(null);
        }
    }

    @Override
    public String toString() {
        return "GpbCommand{" +
                "instance=" + instance +
                ", methodImpl=" + methodImpl +
                ", context=" + context +
                '}';
    }
}
