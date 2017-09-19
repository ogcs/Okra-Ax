package org.okraAx.room.fy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.PlayerRoomCallback;
import org.okraAx.internal.net.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.room.bean.PlayerInfo;
import org.okraAx.room.module.Room;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ.
 * @since 2.0
 * @version  2017.02.12
 */
public final class Player implements ServiceProxy<PlayerRoomCallback> {

    private static final Logger LOG = LogManager.getLogger(LogicClient.class);

    private static final PlayerRoomCallback EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });

    private long uid;
    private volatile NetSession session;
    private final PlayerRoomCallback callback;
    private PlayerInfo info;

    /**
     * 玩家所在的房间.
     * 玩家只能在一个房间中
     */
    private volatile Room room;

    public Player(long uid, NetSession session) {
        this.uid = uid;
        this.session = session;
        this.callback = newProxyInstance(new GpbInvocationHandler(session));
    }

    public boolean isOnline() {
        return session != null && session.isActive();
    }

    public void connected() {

    }

    public void disconnected() {
        if (session != null) {
            session.close();
            this.session = null;
        }
        //  断线 - 直接离开房间
        if (room != null) {
            room.onExit(id());
            room = null;
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

    public PlayerInfo getInfo() {
        return info;
    }

    public void setInfo(PlayerInfo info) {
        this.info = info;
    }

    /**
     *
     */
    public PlayerRoomCallback userClient() {
        if (session == null || !session.isActive() || proxy() == null)
            return EMPTY;
        return proxy();
    }

    public NetSession session() {
        return session;
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
