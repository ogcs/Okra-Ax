package org.okraAx.room.fy.impl;

import org.ogcs.app.AppContext;
import org.okraAx.room.Player;
import org.okraAx.room.component.RoomComponent;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.internal.v3.interfaces.FyRoomService;
import org.okraAx.internal.v3.FySession;
import org.okraAx.internal.v3.AbstractGpbService;
import org.okraAx.v3.room.services.FyRoomSi;

/**
 * @author TinyZ
 * @date 2017-03-02.
 */
//@Component
public final class FyRoomServiceImpl extends AbstractGpbService implements FyRoomService<FySession> {

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);

    public FyRoomServiceImpl() {
        super(FyRoomSi.getDescriptor().findServiceByName("RoomService"));
    }

    @Override
    public void ping(FySession session) {
        session.callback().pong();
    }

    @Override
    public void onCreateRoom(FySession session) {

    }

    @Override
    public void onEnterRoom(FySession session, int roomId, int seat, long uid, String name) {
        Player player = (Player) session.getConnector();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) {
            room = roomComponent.lookupRoom(1);
            if (room == null) {
                room = new ChineseChess(roomId);
                roomComponent.playerJoin(player.id(), player.getRoom());
            }
        }
        room.onEnter(player);
    }

    @Override
    public void onExitRoom(FySession session) {
        Player player = (Player) session.getConnector();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        room.onExit(player.id());
    }

    @Override
    public void onShowHall(FySession session) {

    }

    @Override
    public void onGetReady(FySession session, long uid, boolean ready) {
        Player player = (Player) session.getConnector();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        if (room instanceof ChineseChess) {
            ChineseChess table = (ChineseChess) room;
            table.onReady(player.id(), ready);
        }
    }

    @Override
    public void onGameStart(FySession session) {

    }

    @Override
    public void onGameEnd(FySession session) {

    }

    @Override
    public void onChat(FySession session) {

    }
}
