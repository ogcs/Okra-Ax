package org.okraAx.room.fy.impl;

import com.google.protobuf.Descriptors.ServiceDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.RoomPublicService;
import org.okraAx.internal.v3.AbstractGpbService;
import org.okraAx.internal.v3.FySession;
import org.okraAx.room.Player;
import org.okraAx.room.component.RoomComponent;
import org.okraAx.room.module.Room;
import org.okraAx.room.module.chess.ChineseChess;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.room.services.FyRoomSi;

/**
 * @author TinyZ
 * @date 2017-03-02.
 */
//@Component
public final class RoomPublicImpl extends AbstractGpbService implements RoomPublicService {

    private static final Logger LOG = LogManager.getLogger(RoomPublicImpl.class);
    private static final ServiceDescriptor DESC =
            FyRoomSi.getDescriptor().findServiceByName("RoomService");

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);

    @Override
    public ServiceDescriptor desc() {
        return DESC;
    }

    @Override
    public void ping() {
        FySession session = SessionHelper.currentSession();
        if (session != null) {
            session.callback().pong();
            LOG.info("xxxxx");
        }
    }

    @Override
    public void onCreateRoom() {

    }

    @Override
    public void onEnterRoom(int roomId, int seat, long uid, String name) {
        Player player = SessionHelper.curPlayer();
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
    public void onExitRoom() {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        room.onExit(player.id());
    }

    @Override
    public void onShowHall() {

    }

    @Override
    public void onGetReady(long uid, boolean ready) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room == null) return;
        room.onReady(player, ready);
    }

    @Override
    public void onGameStart() {

    }

    @Override
    public void onGameEnd() {

    }

    @Override
    public void onChat() {

    }
}
