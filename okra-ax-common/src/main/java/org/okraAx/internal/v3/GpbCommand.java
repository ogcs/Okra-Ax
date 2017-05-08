package org.okraAx.internal.v3;

import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.Method;
import java.util.Collection;

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
    private GpbMethodDesc methodDesc;
    private GpbServerContext context;

    public GpbCommand(Object instance, Method methodImpl, GpbMethodDesc methodDesc) {
        this.instance = instance;
        this.methodImpl = methodImpl;
        this.methodDesc = methodDesc;
    }

    public GpbCommand(Object instance, Method methodImpl, GpbServerContext context) {
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
            LOG.error("[Gpb] RPC invoke error.", e);
        } finally {
            SessionHelper.setSession(null);
        }
    }
}
