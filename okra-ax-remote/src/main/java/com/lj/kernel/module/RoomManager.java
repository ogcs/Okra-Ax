package com.lj.kernel.module;

import com.lj.kernel.remote.SpringContext;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
@Service(SpringContext.MODULE_ROOM_MANAGER)
public class RoomManager {

    // roomId - room
    private static final Map<Long, Room> ROOMS = new ConcurrentHashMap<>();

    // uid - Room
    private static final Map<Long, Room> PLAYERS = new ConcurrentHashMap<>();

    public Room get(long roomId) {
        return ROOMS.get(roomId);
    }

    public Room getByUid(long uid) {
        return PLAYERS.get(uid);
    }

    public Set<Room> getAllRoom() {
        return ROOMS.values().stream().collect(Collectors.toSet());
    }

    public void put(long uid, Room room) {
        ROOMS.put(room.id(), room);
        PLAYERS.put(uid, room);
    }

    public void destroy(long roomId) {
        Room room = ROOMS.remove(roomId);
        if (room != null) {
            Set<Long> players = room.players();
            for (Long uid : players) {
                PLAYERS.remove(uid);
            }
            room.destroy();
        }
    }

    public void exit(long uid) {
        Room room = PLAYERS.remove(uid);
        if (room != null) {
            room.exit(uid);
        }
    }
}
