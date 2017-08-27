package org.okraAx.room.module;

import org.okraAx.internal.events.EventDispatcher;
import org.okraAx.internal.events.TriggeredEvent;
import org.okraAx.room.bean.PlayerInfo;
import org.okraAx.room.fy.Player;

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
    protected volatile long roomId;
    /**
     * 房间状态
     */
    protected volatile int status;
    /**
     * 玩家列表
     */
    protected Map<Long/* uid */, Player> players = new ConcurrentHashMap<>();

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
    public Set<Player> players() {
        return new HashSet<>(players.values());
    }

    @Override
    public void onEnter(Player player) {
        if (isFully()) {
            player.userClient().callbackEnterTable(-1);
            return;
        }
        synchronized (this) {
            if (player.getRoom() != null) {
                player.userClient().callbackEnterTable(-2);
                return;
            }
            //
            player.userClient().callbackEnterTable(0);

            players.put(player.id(), player);
            player.setRoom(this);
            //  广播进入房间
            PlayerInfo info = player.getInfo();
            for (Player p : players.values()) {
                p.userClient().callbackJoinRoom(0, info.getUid(), info.getName(), info.getFigure());
            }
        }
    }

    @Override
    public void onReady(Player player, boolean ready) {

        for (Player p : players.values()) {
            p.userClient().callbackChangeUserStatus(0, 1);
        }
    }

    @Override
    public void onExit(Long uid) {
        Player player = players.remove(uid);
        if (player != null) {
            player.setRoom(null);
            //  广播离开房间
            for (Player p : players.values()) {
                p.userClient().callbackExitRoom(0, uid);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (!players.isEmpty()) {
            for (Player player : players.values()) {
                player.disconnected();
            }
            players.clear();
        }
    }

    /**
     * Send message to every player on table.
     *
     * @param msg the message send to player.
     */
    protected void broadcast(Object msg) {
        for (Player player : players.values()) {
            if (player.isOnline()) {
                player.session().writeAndFlush(msg);
            }
        }
    }

    /**
     * push message to special player by uid.
     *
     * @param uid the player's unique id.
     * @param msg the message send to player.
     */
    protected void push(long uid, Object msg) {
        Player player = players.get(uid);
        if (player != null) {
            player.session().writeAndFlush(msg);
        }
    }

    public void onEvent(String type, Player source) {
        super.dispatchEvent(new TriggeredEvent<Room, Player>(type, this, source));
    }
}
