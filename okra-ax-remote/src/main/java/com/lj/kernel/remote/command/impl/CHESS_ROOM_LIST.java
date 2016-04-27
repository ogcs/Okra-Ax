package com.lj.kernel.remote.command.impl;

import com.lj.kernel.ax.AxReplys;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.message.GpbChess.ResChessRoomInfo;
import com.lj.kernel.module.Room;
import com.lj.kernel.remote.command.RemoteCommand;
import org.ogcs.app.Session;

import java.util.Set;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/14
 */
public class CHESS_ROOM_LIST extends RemoteCommand {

    @Override
    public void execute(Session session, Inbound request) throws Exception {
        Set<Room> allRoom = roomManager.getAllRoom();
        ResChessRoomInfo.Builder builder = ResChessRoomInfo.newBuilder();
        for (Room room : allRoom) {
            ResChessRoomInfo.RoomInfo.Builder roomInfo = ResChessRoomInfo.RoomInfo.newBuilder();
            roomInfo.setId((int) room.id());
            Set<Long> players = room.players();
            for (Long uid : players) {
                roomInfo.addPlayers("visual - " + uid.toString());
            }
            builder.addInfos(roomInfo);
        }
        session.writeAndFlush(AxReplys.outbound(AxReplys.response(request.getId(), builder), request.getUid()));
    }
}
