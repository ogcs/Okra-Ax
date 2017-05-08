package org.okraAx.room.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.Session;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.RoomService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.room.fy.Player;
import org.okraAx.utilities.SessionHelper;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
public enum Facade implements RoomService, RoomPublicService, FyChessService {

    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Facade.class);

    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private ChessComponent chessComponent = AppContext.getBean(ChessComponent.class);

    //  public procedure invoked by player

    @Override
    public void ping() {
        Player player = SessionHelper.curPlayer();
        if (player != null)
            player.userClient().pong();
        LOG.info("xxxxx");
    }

    @Override
    public void onPlayerConnect(long security) {
        NetSession session = SessionHelper.currentSession();
        if (session == null || !session.isActive()) return;
        // TODO: verify security code from login server.

        Player player = new Player(-1L, session);
        player.userClient().pong();
    }

    @Override
    public void onCreateRoom() {

    }

    @Override
    public void onEnterRoom(int roomId, int seat, long uid, String name) {
        roomComponent.onEnterRoom(roomId, seat, uid, name);
    }

    @Override
    public void onExitRoom() {
        roomComponent.onExitRoom();
    }

    @Override
    public void onShowHall() {

    }

    @Override
    public void onGetReady(long uid, boolean ready) {
        roomComponent.onGetReady(uid, ready);
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

    @Override
    public void onShowRoomStatus() {

    }

    @Override
    public void onMoveChess(int fromX, int fromY, int toX, int toY) {
        chessComponent.onMoveChess(fromX, fromY, toX, toY);
    }

    @Override
    public void enterChannel() {

    }

    @Override
    public void callbackVerifyPlayerAuth(int ret) {

    }

    @Override
    public void callbackRegister(int ret) {

    }



}
