package org.okraAx.login.component;

import org.ogcs.app.NetSession;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.internal.v3.ProxySession;
import org.okraAx.login.server.LoginUser;
import org.okraAx.utilities.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public class Facade implements LoginPublicService, LoginForRoomService {

    @Autowired
    private LoginComponent loginComponent;
    @Autowired
    private RoomComponent roomComponent;

    @Override
    public void onCreateRole(String openId, String name, int figure) {
        NetSession session = SessionHelper.currentSession();
        if (session == null) return;
        loginComponent.onCreateRole(session, openId, name, figure);
    }

    @Override
    public void onLogin(String openId) {
        loginComponent.onLogin(openId);
    }

    @Override
    public void onSyncTime() {
        loginComponent.onSyncTime();
    }

    @Override
    public void onShowChannelList() {

    }

    @Override
    public void onEnterChannel() {
        LoginUser player = SessionHelper.curPlayer();
        if (player == null) return;
        //  TODO: 校验房间信息


        ProxySession<LoginForRoomService> session = null;

        player.userClient().callbackEnterChannel(1);
    }


    //  logic for room


    public void registerChannel(String security, long version, String host, int roomId, int type, int port) {
        roomComponent.registerChannel(security, version, roomId, type, host, port);
    }

    @Override
    public void registerChannel() {
        System.out.println();
    }

    @Override
    public void verifyPlayerInfo(long uid, long security) {

    }

    @Override
    public void callbackEnterChannel(int ret) {

    }

}
