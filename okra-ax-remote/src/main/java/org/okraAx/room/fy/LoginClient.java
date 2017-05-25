package org.okraAx.room.fy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.internal.v3.protobuf.GpbMessageContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * room组件连接和访问logic组件的接口.
 *
 * @author TinyZ.
 * @version 2017.03.28
 * @since 2.0
 */
public final class LoginClient implements ServiceProxy<LoginForRoomService> {

    private static final Logger LOG = LogManager.getLogger(LoginClient.class);
    /**
     * The fake session for real logic.
     */
    private static final LoginForRoomService EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });

    private LoginForRoomService service;
    private NetSession session;
    private GpbMessageContext messageContext = AppContext.getBean(GpbMessageContext.class);

    public LoginClient() {
    }

    public LoginForRoomService newOutputProxy() {
//        return newProxyInstance((proxy, method, args) -> {
//            if (session != null && session.isActive()) {
//                GpcCall call = this.messageContext.pack(method, args);
//                session.writeAndFlush(call);
//            }
//            return null;
//        });
        return newProxyInstance(new GpbInvocationHandler(session));
    }

    public LoginForRoomService loginClient() {
        return proxy() == null ? EMPTY : proxy();
    }

    /**
     * Create new proxy instance for {@link LoginForRoomService}.
     */
    private static LoginForRoomService newProxyInstance(InvocationHandler handler) {
        return (LoginForRoomService) Proxy.newProxyInstance(
                LoginClient.class.getClassLoader(),
                new Class[]{LoginForRoomService.class},
                handler);
    }

    public void setSession(NetSession session) {
        if (session != null && session.isActive()) {
            this.session = session;
            service = newOutputProxy();
        } else {
            this.session = null;
            this.service = null;
        }
    }

    @Override
    public LoginForRoomService proxy() {
        return service;
    }
}
