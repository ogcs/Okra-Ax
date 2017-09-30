package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.LogicService;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * [login -> logic]
 */
public final class LogicClient implements ServiceProxy<LogicService> {

    private static final Logger LOG = LogManager.getLogger(LogicClient.class);
    /**
     * The fake session for real logic.
     */
    private static final LogicService EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]. args:{}", method.getName(), args);
        return null;
    });

    private LogicService service;
    private final NetSession session;
    public final ConnectionInfo info;

    public LogicClient(NetSession session, ConnectionInfo info) {
        this.session = session;
        this.service = newProxyInstance(new GpbInvocationHandler(session));
        this.info = info;
    }

    public LogicService logicClient() {
        return proxy() == null ? EMPTY : proxy();
    }

    /**
     * Create new proxy instance for {@link LoginForRoomService}.
     */
    private static LogicService newProxyInstance(InvocationHandler handler) {
        return (LogicService) Proxy.newProxyInstance(
                LogicService.class.getClassLoader(),
                new Class[]{LogicService.class},
                handler);
    }

    @Override
    public LogicService proxy() {
        return service;
    }
}
