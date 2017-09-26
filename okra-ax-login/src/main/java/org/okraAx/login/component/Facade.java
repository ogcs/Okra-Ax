package org.okraAx.login.component;

import org.okraAx.common.LoginForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.login.server.User;
import org.okraAx.utilities.NetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public class Facade implements LoginPublicService, LoginForRoomService {

    @Autowired
    private RoomComponent roomComponent;
    @Autowired
    private UserComponent userComponent;

    @Override
    public void onCreateRole(String openId, String name, int figure) {
        NetSession session = NetHelper.session();
        if (session != null)
            userComponent.onCreateRole(session, openId, name, figure);
    }

    @Override
    public void onLogin(String openId) {
        userComponent.onLogin(openId);
    }

    @Override
    public void onSyncTime() {
        userComponent.onSyncTime();
    }

    @Override
    public void onShowChannelList() {

    }

    @Override
    public void onEnterChannel() {
        User user = userComponent.getUserBySession(NetHelper.session());
        if (user != null) {
            //  TODO: 校验房间信息

            user.callback().callbackEnterChannel(1);
        }
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
