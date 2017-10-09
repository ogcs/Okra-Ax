package org.okraAx.internal.v3.protobuf;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.RelayService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.utilities.ProxyUtil;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <pre>
 *     1. {@link GpbMessageContext}依赖Spring框架注入.
 * </pre>
 *
 * @author TinyZ.
 * @version 2017.10.03
 * @see ServerContext
 */
public final class GpbRelayInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LogManager.getLogger(GpbRelayInvocationHandler.class);

    private static final RelayService EMPTY_RELAY_SERVICE =
            ProxyUtil.newProxyInstance(RelayService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[GpbRelayInvocationHandler] error: [{}]", method.getName());
                return null;
            });

    private final ThreadLocal<Long> target = new ThreadLocal<>();
    private final RelayService service;
    private GpbMessageContext context;

    public GpbRelayInvocationHandler(NetSession session) {
        this.service = GpbProxyUtil.newProxyClient(session, EMPTY_RELAY_SERVICE).impl();
        this.context = AppContext.getBean(GpbMessageContext.class);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (this.context == null) {
                LOG.error("[Gpb Relay] context error. method:" + method.getName());
                return null;
            }
            GpcCall relay = this.context.pack(method, args);
            service.onRelay(this.target.get(), relay);
        } catch (Exception e) {
            LOG.error("[Gpb Relay] method[" + method.getName() + "] invoke exception. ", e);
        } finally {
            this.target.remove();
        }
        return null;
    }

    public void setExtraTag(long target) {
        this.target.set(target);
    }
}
