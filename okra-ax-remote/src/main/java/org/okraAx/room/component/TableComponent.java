package org.okraAx.room.component;

import org.okraAx.room.bean.RemotePlayerInfo;
import org.okraAx.room.bean.RoomInfoBean;
import org.okraAx.room.fy.RemoteUser;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Service;

import java.util.List;
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
public final class TableComponent {

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


    public void createRoom(RemotePlayerInfo remotePlayerInfo, int type) {

    }

    /**
     * 加入房间
     *
     * @param roomId 指定房间
     * @param seat   指定位置
     */
    public void joinTable(RemotePlayerInfo remotePlayerInfo, long roomId, int seat) {
        Room room = roomMap.get(roomId);
        if (room == null || room.isFully()) {
            return;
        }
        synchronized (room) {
            if (!room.isFully()) {

            }
        }


    }

    public void batchJoinRoom(List<RemotePlayerInfo> list, long roomId) {
        Room room = roomMap.get(roomId);
        if (room == null || room.isFully()) {
            return;
        }
        synchronized (room) {
            if (!room.isFully()) {

            }
        }


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
        if (normal) {
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
            Set<RemoteUser> remoteUsers = room.players();
            for (RemoteUser remoteUser : remoteUsers) {
                uid2RoomMap.remove(remoteUser.id());
            }
            room.onDestroy();
        }
    }

    public void onEnterRoom(int roomId, int seat, long uid, String name) {
        RemoteUser remoteUser = SessionHelper.curPlayer();
        if (remoteUser == null) return;
        Room room = remoteUser.getRoom();
        if (room == null) {
            room = lookupRoom(1);
            if (room == null) {
                room = new ChineseChess(roomId);
                playerJoin(remoteUser.id(), remoteUser.getRoom());
            }
        }
        room.onEnter(remoteUser);
    }

    public void onExitRoom() {
        RemoteUser remoteUser = SessionHelper.curPlayer();
        if (remoteUser == null) return;
        Room room = remoteUser.getRoom();
        if (room == null) return;
        room.onExit(remoteUser.id());
    }

    public void onGetReady(long uid, boolean ready) {
        RemoteUser remoteUser = SessionHelper.curPlayer();
        if (remoteUser == null) return;
        Room room = remoteUser.getRoom();
        if (room == null) return;
        room.onReady(remoteUser, ready);
    }

}
