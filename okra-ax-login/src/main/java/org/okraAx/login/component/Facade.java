package org.okraAx.login.component;

import org.okraAx.common.LogicForRoomService;
import org.okraAx.common.LogicPublicService;
import org.okraAx.internal.v3.ProxySession;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
public enum Facade implements LogicPublicService, LogicForRoomService {

    INSTANCE;

    private LoginComponent loginComponent = new LoginComponent();
    private RoomComponent roomComponent = new RoomComponent();

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
