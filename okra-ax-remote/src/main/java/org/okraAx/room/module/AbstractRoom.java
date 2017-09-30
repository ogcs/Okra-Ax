package org.okraAx.room.module;

import org.okraAx.internal.events.EventDispatcher;
import org.okraAx.internal.events.TriggeredEvent;
import org.okraAx.room.bean.RemotePlayerInfo;
import org.okraAx.room.fy.RemoteUser;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ
 * @since 2.0
 */
public abstract class AbstractRoom extends EventDispatcher implements Room {

    /**
     * 房间唯一ID
     */
    private final long roomId;
    /**
     * 房间状态
     */
    protected volatile int status;
    /**
     * 玩家列表
     */
    protected final Map<Long/* uid */, RemoteUser> players = new ConcurrentHashMap<>();

    public AbstractRoom(long roomId) {
        this.roomId = roomId;
    }

    @Override
    public long id() {
        return roomId;
    }

    @Override
    public int status() {
        return status;
    }

    @Override
    public boolean isFully() {
        return players.size() >= maxPlayer();
    }

    @Override
    public Set<RemoteUser> players() {
        return new HashSet<>(players.values());
    }

    @Override
    public boolean onEnter(RemoteUser user) {
        if (isFully()) {
            user.callback().callbackEnterTable(-1);
            return false;
        }
        synchronized (this) {
            if (user.getRoom() != null) {
                user.callback().callbackEnterTable(-2);
                return false;
            }
            players.put(user.id(), user);
            user.setRoom(this);

            user.callback().callbackEnterTable(0);

            //  广播进入房间
            RemotePlayerInfo info = user.userInfo();
            for (RemoteUser p : players.values()) {
                p.callback().callbackJoinRoom(0, info.getUid(), info.getName(), info.getFigure());
            }
            return true;
        }
    }

    @Override
    public boolean onEnterWithPosition(RemoteUser user, int position) {
        return false;
    }

    @Override
    public void onReady(RemoteUser remoteUser, boolean ready) {

        for (RemoteUser p : players.values()) {
            p.callback().callbackChangeUserStatus(0, 1);
        }
    }

    @Override
    public void onExit(Long uid) {
        RemoteUser remoteUser = players.remove(uid);
        if (remoteUser != null) {
            remoteUser.setRoom(null);
            //  广播离开房间
            for (RemoteUser p : players.values()) {
                p.callback().callbackExitRoom(0, uid);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (!players.isEmpty()) {
            for (RemoteUser remoteUser : players.values()) {
                remoteUser.closeSession();
            }
            players.clear();
        }
    }

    public void onEvent(String type, RemoteUser source) {
        super.dispatchEvent(new TriggeredEvent<Room, RemoteUser>(type, this, source));
    }
}
