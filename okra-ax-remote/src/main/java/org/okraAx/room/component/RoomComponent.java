package org.okraAx.room.component;

import org.okraAx.room.Player;
import org.okraAx.room.module.Room;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author TinyZ
 * @date 2017-02-11.
 */
@Component
public class RoomComponent {

    private final Map<Long/* roomId */, Room> roomMap = new ConcurrentHashMap<>();
    private final Map<Long/* uid */, Room> uid2RoomMap = new ConcurrentHashMap<>();

    public Room get(long roomId) {
        return roomMap.get(roomId);
    }

    public Room lookupRoom(int amount) {
        for (Room room : roomMap.values()) {
            if (!room.isFully()) {
                return room;
            }
        }
        return null;
    }

    public Room getByUid(long uid) {
        return uid2RoomMap.get(uid);
    }

    public Set<Room> getAllRoom() {
        return roomMap.values().stream().collect(Collectors.toSet());
    }

    public void playerJoin(long uid, Room room) {
        roomMap.put(room.id(), room);
        uid2RoomMap.put(uid, room);
    }

    public void destroyRoom(long roomId) {
        Room room = roomMap.remove(roomId);
        if (room != null) {
            Set<Player> players = room.players();
            for (Player player : players) {
                uid2RoomMap.remove(player.id());
            }
            room.onDestroy();
        }
    }

    public void playerExit(long uid) {
        Room room = uid2RoomMap.remove(uid);
        if (room != null) {
            room.onExit(uid);
        }
    }

}
