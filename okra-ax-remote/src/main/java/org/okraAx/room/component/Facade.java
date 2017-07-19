package org.okraAx.room.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.RoomService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.room.fy.Player;
import org.okraAx.room.module.Room;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.beans.roomPub.MsgOnChat;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
public enum Facade implements RoomService, RoomPublicService,
        FyChessService {

    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(Facade.class);

    private LoginComponent lc = AppContext.getBean(LoginComponent.class);
    private RoomComponent roomComponent = AppContext.getBean(RoomComponent.class);
    private ChessComponent chessComponent = AppContext.getBean(ChessComponent.class);

    //  public procedure invoked by player

    public void initialize() {

    }

    @Override
    public void ping() {
        Player player = SessionHelper.curPlayer();
        if (player != null)
            player.userClient().pong();
        LOG.info("xxxxx");
    }

    @Override
    public void onPlayerConnect(long uid, long security) {
        NetSession session = SessionHelper.currentSession();
        if (session == null || !session.isActive()) return;
        // TODO: verify security code from login server.
        //  login通知

        Player player = new Player(uid, session);
        session.setConnector(player);
        //  logic校验用户信息
        lc.service().verifyPlayerInfo(uid, security);
    }

    public void callbackVerifyPlayerInfo(int ret) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;

        //  set player info

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
        roomComponent.showRoomList();
    }

    @Override
    public void onGetReady(boolean ready) {
        roomComponent.onGetReady(ready);
    }

    @Override
    public void onChat(MsgOnChat chat) {
        Player player = SessionHelper.curPlayer();
        if (player == null) return;
        Room room = player.getRoom();
        if (room != null) {
            //  TODO: 脏词过滤 - 客户端过滤
            room.broadcast(chat);
            LOG.info("chat message content. ", chat.toString());
        }
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
