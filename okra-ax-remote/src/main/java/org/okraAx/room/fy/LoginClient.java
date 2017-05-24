package org.okraAx.room.fy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.LogicForRoomService;
import org.okraAx.internal.v3.ClientContext;
import org.okraAx.internal.v3.ConnectionEventHandler;
import org.okraAx.internal.v3.MtdDescUtil;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * room组件连接和访问logic组件的接口.
 *
 * @author TinyZ.
 * @version 2017.03.28
 * @since 2.0
 */
public final class LoginClient implements ServiceProxy<LogicForRoomService> {

    private static final Logger LOG = LogManager.getLogger(LoginClient.class);
    /**
     * The fake session for real logic.
     */
    private static final LogicForRoomService EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });

    private ClientContext context;
    private LogicForRoomService service;
    private NetSession session;
    private GpbMessageContext messageContext = AppContext.getBean(GpbMessageContext.class);

    public LoginClient(ClientContext context) {
        this.context = context;
    }

    public LogicForRoomService newOutputProxy() {
        return newProxyInstance((proxy, method, args) -> {
            if (session != null && session.isActive()) {
                GpcCall call = this.messageContext.pack(method, args);
                session.writeAndFlush(call);
            }
            return null;
        });
    }

    public LogicForRoomService loginClient() {
        if (proxy() == null) {
            if (session != null && session.isActive()) {
                service = newOutputProxy();
            }
            return EMPTY;
        }
        return proxy();
    }

    /**
     * Create new proxy instance for {@link LogicForRoomService}.
     */
    private static LogicForRoomService newProxyInstance(InvocationHandler handler) {
        return (LogicForRoomService) Proxy.newProxyInstance(
                LoginClient.class.getClassLoader(),
                new Class[]{LogicForRoomService.class},
                handler);
    }

    public void setSession(NetSession session) {
        this.session = session;
    }

    @Override
    public LogicForRoomService proxy() {
        return service;
    }
}
