package org.okraAx.room.module;

import org.ogcs.app.Session;
import org.ogcs.event.Event;
import org.ogcs.event.EventDispatcher;
import org.ogcs.event.MultiListenerEventDispatcher;
import org.okraAx.room.fy.Player;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author TinyZ
 * @since 2.0
 */
public abstract class AbstractTable implements Room {

    protected long roomId;
    protected Map<Long/* uid */, Session> sessions = new ConcurrentHashMap<>();
    protected EventDispatcher dispatcher = new MultiListenerEventDispatcher();

    @Override
    public long id() {
        return roomId;
    }

    @Override
    public boolean isFully() {
        return sessions.size() >= maxPlayer();
    }

    @Override
    public Set<Player> players() {
        return sessions.values().stream()
                .map(session -> (Player) session.getConnector())
                .collect(Collectors.toSet());
    }

    @Override
    public synchronized void onEnter(Player player) {
        if (isFully()) {
            player.userClient().callbackEnterTable(-1);
            return;
        }
        if (player.getRoom() != null) {
            player.userClient().callbackEnterTable(-2);
            return;
        }
        //  广播进入房间
        player.userClient().callbackEnterTable(0);
        //
        sessions.put(player.id(), player.session());
        player.setRoom(this);
    }

    @Override
    public void onReady(Player player, boolean ready) {

    }

    @Override
    public void onExit(Long uid) {
        Session remove = sessions.remove(uid);
        if (remove != null) {
            Player player = (Player) remove.getConnector();
            player.setRoom(null);
            //  广播离开房间

        }
    }

    @Override
    public void onDestroy() {
        sessions.clear();
    }

    protected void dispatchEvent(Event event) {
        dispatcher.dispatchEvent(event);
    }

    protected void dispatchEvent(Object type, Object source) {
        dispatcher.dispatchEvent(type, this, source);
    }

    @Override
    public void broadcast(Object msg) {
        for (Session session : sessions.values()) {
            if (session.isActive()) {
                session.writeAndFlush(msg);
            }
        }
    }

    @Override
    public void push(long uid, Object msg) {
        Session session = sessions.get(uid);
        if (session != null) {
            session.writeAndFlush(msg);
        }
    }
}
