package org.okraAx.room.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.RoomService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.internal.net.NetSession;
import org.okraAx.room.fy.Player;
import org.okraAx.room.module.Room;
import org.okraAx.utilities.NetHelper;
import org.okraAx.utilities.SessionHelper;
import org.okraAx.v3.beans.roomPub.MsgOnChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public final class Facade implements RoomService, RoomPublicService,
        FyChessService {

    private static final Logger LOG = LogManager.getLogger(Facade.class);

    @Autowired
    private LoginComponent lc ;
    @Autowired
    private RoomComponent roomComponent ;
    @Autowired
    private ChessComponent chessComponent;
    @Autowired
    private PlayerComponent playerComponent;

    //  public procedure invoked by player

    public void initialize() {

    }

    @Override
    public void ping() {
        Player player = playerComponent.getPlayer(NetHelper.session());
        if (player != null)
            player.userClient().pong();
        LOG.info("xxxxx");
    }

    @Override
    public void onPlayerConnect(long uid, long security) {
        NetSession session = NetHelper.session();
        if (session == null || !session.isActive()) return;
        // TODO: verify security code from login server.
        //  login通知

        Player player = new Player(uid, session);
        //  TODO:

        //  logic校验用户信息
        lc.service().verifyPlayerInfo(uid, security);
    }

    public void syncInfoAfterConnect() {

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
        Player player = playerComponent.getPlayer(NetHelper.session());
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
