package org.okraAx.room.fy;

import org.ogcs.app.Connector;
import org.ogcs.app.Session;
import org.okraAx.room.module.Room;

/**
 * @author TinyZ
 * @date 2017-02-12.
 */
public class Player implements Connector {

    private long uid;
    private volatile Session session;
    /**
     * 玩家所在的房间.
     * 玩家只能在一个房间中
     */
    private volatile Room room;

    public Player(long uid, Session session) {
        this.uid = uid;
        this.session = session;
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
}
