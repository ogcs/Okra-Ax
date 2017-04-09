package org.okraAx.login.component;

import org.okraAx.common.LogicForRoomService;
import org.okraAx.common.LogicPublicService;
import org.okraAx.internal.v3.ProxySession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public class Facade implements LogicPublicService, LogicForRoomService {

    @Autowired
    private LoginComponent loginComponent;
    @Autowired
    private RoomComponent roomComponent;

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

        //  SessionHelper.currentSession()
        //  TODO: 校验房间信息


        ProxySession<LogicForRoomService> session = null;

        session.invoker().callbackEnterChannel(1);
    }


    //  logic for room

    public void registerChannel(String security, long version, String host, int roomId, int type, int port) {
        roomComponent.registerChannel(security, version, roomId, type, host, port);
    }

    @Override
    public void registerChannel() {

    }

    @Override
    public void callbackEnterChannel(int ret) {

    }


}
