package org.okraAx.login.server;

import org.ogcs.app.AppContext;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.RoomService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.login.bean.ChannelInfo;
import org.okraAx.login.component.RoomComponent;

import java.lang.reflect.Proxy;

/**
 * login组件管理room信息和建立的连接.
 * @author TinyZ.
 * @version 2017.03.29
 */
public class RoomClient implements ServiceProxy<RoomService> {

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private final ChannelInfo channelInfo;
    private volatile NetSession session;
    private volatile RoomService service;

    public RoomClient(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    public RoomService roomClient() {
        return this.service;
    }

    public boolean isOnline() {
        return session != null && session.isActive();
    }

    public NetSession session() {
        return session;
    }

    public void setSession(NetSession session) {
        //  init proxy session.
        if (this.service == null) {
            synchronized (this) {
                if (this.service == null) {
                    this.service = (RoomService) Proxy.newProxyInstance(
                            this.getClass().getClassLoader(),
                            new Class[]{RoomService.class}, new GpbInvocationHandler(session)
                    );
                }
            }
        }
        this.session = session;
    }

    @Override
    public RoomService proxy() {
        return service;
    }
}
