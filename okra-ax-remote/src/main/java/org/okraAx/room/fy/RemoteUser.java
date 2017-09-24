package org.okraAx.room.fy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.net.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.room.bean.RemotePlayerInfo;
import org.okraAx.room.module.Room;
import org.okraAx.utilities.ProxyUtil;

/**
 * @author TinyZ.
 * @version 2017.02.12
 * @since 2.0
 */
public final class RemoteUser {

    private static final Logger LOG = LogManager.getLogger(RemoteUser.class);

    private static final PlayerCallback DEFAULT_CALLBACK =
            ProxyUtil.newProxyInstance(PlayerCallback.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });
    private final RemotePlayerInfo remotePlayerInfo;
    private final ProxyClient<PlayerCallback> client;
    /**
     * 玩家所在的房间.
     * 玩家只能在一个房间中
     */
    private volatile Room room;

    public RemoteUser(RemotePlayerInfo bean, NetSession session) {
        this.remotePlayerInfo = bean;
        this.client = new ProxyClient<>(session, DEFAULT_CALLBACK);
    }

    public PlayerCallback callback() {
        return this.client.impl();
    }

    public boolean isOnline() {
        return this.client.isActive();
    }

    public void closeSession() {
        NetSession session = this.client.getSession();
        if (session != null)
            session.close();
    }

    public long id() {
        return remotePlayerInfo.getUid();
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public RemotePlayerInfo userInfo() {
        return remotePlayerInfo;
    }
}
