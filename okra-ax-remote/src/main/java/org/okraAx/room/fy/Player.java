package org.okraAx.room.fy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Connector;
import org.ogcs.app.ServiceProxy;
import org.ogcs.app.Session;
import org.okraAx.common.PlayerRoomCallback;
import org.okraAx.internal.v3.GpbInvocationHandler;
import org.okraAx.room.module.Room;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ.
 * @since 2.0
 * @version  2017.02.12
 */
public final class Player implements Connector, ServiceProxy<PlayerRoomCallback> {

    private static final Logger LOG = LogManager.getLogger(LogicClient.class);

    private static final PlayerRoomCallback EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });

    private long uid;
    private volatile Session session;
    private final PlayerRoomCallback callback;
    /**
     * 玩家所在的房间.
     * 玩家只能在一个房间中
     */
    private volatile Room room;

    public Player(long uid, Session session) {
        this.uid = uid;
        this.session = session;
        this.callback = newProxyInstance(new GpbInvocationHandler(session));
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
        this.session = session;
    }

    @Override
    public void sessionActive() {

    }

    @Override
    public void sessionInactive() {
        if (session != null) {
            session.close();
            this.session = null;
        }
    }

    public long id() {
        return uid;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     *
     */
    public PlayerRoomCallback userClient() {
        if (session == null || !session.isActive() || proxy() == null)
            return EMPTY;
        return proxy();
    }

    @Override
    public PlayerRoomCallback proxy() {
        return this.callback;
    }

    private static PlayerRoomCallback newProxyInstance(InvocationHandler handler) {
        return (PlayerRoomCallback) Proxy.newProxyInstance(
                LogicClient.class.getClassLoader(),
                new Class[]{PlayerRoomCallback.class},
                handler);
    }
}
