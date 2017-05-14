package org.okraAx.login.role.mybatis;

import org.ogcs.app.AppContext;
import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.okraAx.common.RoomService;
import org.okraAx.internal.v3.GpbInvocationHandler;
import org.okraAx.login.bean.ChannelInfo;
import org.okraAx.login.component.RoomComponent;

import java.lang.reflect.Proxy;

/**
 * login组件管理room信息和建立的连接.
 * @author TinyZ.
 * @version 2017.03.29
 */
public class RoomClient implements Connector<Session> {

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private final ChannelInfo channelInfo;
    private volatile Session session;
    private volatile RoomService service;

    public RoomClient(ChannelInfo channelInfo) {
        this.channelInfo = channelInfo;
    }

    public RoomService roomClient() {
        return this.service;
    }

    @Override
    public boolean isOnline() {
        return session != null && session.isActive();
    }

    @Override
    public Session session() {
        return session;
    }

    @Override
    public void setSession(Session session) {
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
    public void sessionActive() {

    }

    @Override
    public void sessionInactive() {
        this.session = null;
        if (this.roomComponent != null)
            this.roomComponent.unregisterChannel(channelInfo.getRoomId());
    }
}
