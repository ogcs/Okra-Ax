package org.okraAx.room.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.RemoteService;
import org.okraAx.common.RoomPublicService;
import org.okraAx.common.modules.FyChessService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.room.bean.RemotePlayerInfo;
import org.okraAx.room.fy.RemoteUser;
import org.okraAx.room.module.Room;
import org.okraAx.utilities.NetHelper;
import org.okraAx.v3.beans.roomPub.MsgOnChat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public final class Facade implements RemoteService, RoomPublicService,
        FyChessService {

    private static final Logger LOG = LogManager.getLogger(Facade.class);

    @Autowired
    private LoginComponent lc;
    @Autowired
    private RoomComponent roomComponent;
    @Autowired
    private ChessComponent chessComponent;
    @Autowired
    private PlayerComponent playerComponent;

    //  public procedure invoked by player

    /**
     *
     */
    public void initComponent() {
        try {

        } finally {
            lc.initialize();//  连接login， 开始服务
        }
    }

    @Override
    public void ping() {
        RemoteUser remoteUser = playerComponent.getPlayer(NetHelper.session());
        if (remoteUser == null) {
            NetHelper.session().close();
            LOG.info("xxxxx");
            return;
        }
        remoteUser.callback().pong();
    }

    @Override
    public void onPlayerConnect(long uid, long security) {
        NetSession session = NetHelper.session();
        if (session == null || !session.isActive()) return;
        // TODO: verify security code from login server.
        //  login通知
        RemotePlayerInfo remotePlayerInfo = new RemotePlayerInfo();

        RemoteUser remoteUser = new RemoteUser(remotePlayerInfo, session);
        //  TODO:

//        playerComponent.registerUserInfo(remoteUser);

        //  logic校验用户信息
        lc.loginClient().verifyPlayerInfo(uid, security);
    }

    public void syncInfoAfterConnect() {

    }

    public void callbackVerifyPlayerInfo(int ret) {
        RemoteUser remoteUser = playerComponent.getPlayer(NetHelper.session());
        if (remoteUser == null) return;

        //  set player info

        remoteUser.callback().pong();
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
        RemoteUser remoteUser = playerComponent.getPlayer(NetHelper.session());
        if (remoteUser == null) return;
        Room room = remoteUser.getRoom();
        if (room != null) {
            //  TODO: 脏词过滤 - 客户端过滤
            for (RemoteUser user : room.players()) {
                user.callback().pong();
            }
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

    public void registerChannel() {

    }

    public void verifyPlayerInfo(long uid, long security) {

    }

    public void callbackEnterChannel(int ret) {

    }
}
