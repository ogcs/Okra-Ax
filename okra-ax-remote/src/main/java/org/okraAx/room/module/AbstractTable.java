package org.okraAx.room.module;

import com.google.protobuf.Message;
import org.ogcs.app.Session;
import org.ogcs.event.Event;
import org.ogcs.event.EventDispatcher;
import org.ogcs.event.MultiListenerEventDispatcher;
import org.okraAx.internal.inner.IrSession;
import org.okraAx.room.Player;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author TinyZ
 * @date 2017-02-13.
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
    public void onEnter(Player player) {
        if (isFully()) return;
        sessions.put(player.id(), player.session());
        player.setRoom(this);
    }

    @Override
    public void onExit(Long uid) {
        Session remove = sessions.remove(uid);
        if (remove != null) {
            Player player = (Player) remove.getConnector();
            player.setRoom(null);
        }
    }

    protected void dispatchEvent(Event event) {
        dispatcher.dispatchEvent(event);
    }

    protected void dispatchEvent(Object type, Object source) {
        dispatcher.dispatchEvent(type, this, source);
    }

    /**
     * Send message to every player on table.
     *
     * @param message The message send to player.
     */
    protected void broadcast(Message message) {
        for (Session session : sessions.values()) {
            if (session.isOnline()) {
                session.writeAndFlush(message);
            }
        }
    }

    @Override
    public void onDestroy() {
        sessions.clear();
    }
}
