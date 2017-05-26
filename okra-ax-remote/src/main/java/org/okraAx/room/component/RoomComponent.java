package org.okraAx.room.component;

import org.okraAx.room.bean.RoomInfoBean;
import org.okraAx.room.fy.Player;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 房间组件
 *
 * @author TinyZ
 * @version 2017.05.25.
 */
@Service
public final class RoomComponent {

    private final Map<Long/* roomId */, Room> roomMap = new ConcurrentHashMap<>();
    private final Map<Long/* uid */, Room> uid2RoomMap = new ConcurrentHashMap<>();

    public Room get(long roomId) {
        return roomMap.get(roomId);
    }

    /**
     * 搜索房间
     */
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

    /**
     * 创建房间
     */
    public RoomInfoBean createRoom(int type, boolean normal) {
        RoomInfoBean roomInfo = new RoomInfoBean();
        //  roomId
        //
        if (normal){
            //  join room
        }
        return roomInfo;
    }

    public void playerJoin(long uid, Room room) {
        roomMap.put(room.id(), room);
        uid2RoomMap.put(uid, room);
    }

    public void playerExit(long uid) {
        Room room = uid2RoomMap.remove(uid);
        if (room != null) {
            room.onExit(uid);
        }
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

    public void onEnterRoom(int roomId, int seat, long uid, String name) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) {
            room = lookupRoom(1);
            if (room == null) {
                room = new ChineseChess(roomId);
                playerJoin(player.id(), player.getRoom());
            }
        }
        room.onEnter(player);
    }

    public void onExitRoom() {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        room.onExit(player.id());
    }

    public void onGetReady(long uid, boolean ready) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        room.onReady(player, ready);
    }

}
