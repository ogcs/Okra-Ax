package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.common.LoginPublicService;
import org.okraAx.common.RelayService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.login.server.User;
import org.okraAx.utilities.NetHelper;
import org.okraAx.v3.GpcCall;
import org.okraAx.v3.GpcRelay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author TinyZ.
 * @version 2017.03.26
 */
@Service
public class Facade implements RelayService, LoginPublicService, LoginForRoomService {

    private static final Logger LOG = LogManager.getLogger(Facade.class);

    @Autowired
    private RoomComponent roomComponent;
    @Autowired
    private UserComponent userComponent;
    @Autowired
    private RemoteComponent remoteComponent;

    public Facade() {
        throw new NullPointerException("test");
    }

    @Override
    public void onRelay(long source, Object msg) {
        if (msg instanceof GpcCall) {
            User user = userComponent.getUserByUid(source);
            if (user != null && user.proxyClient().isActive()) {
                user.proxyClient().getSession().writeAndFlush(msg);
            }
        } else {
            LOG.error("[onRelay] msg data is wrong. msg:{}", msg == null ? "null" : msg.getClass());
        }
    }

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

    /**
     * @see #enterChannelSuccuss(long, long)
     */
    @Override
    public void onEnterChannel() {
        User user = userComponent.getUserBySession(NetHelper.session());
        if (user != null) {
            //  TODO: 校验房间信息

            remoteComponent.remoteClient(1).enterChannel();


        }
    }

    public void enterChannelSuccuss(long uid, long secutify) {
        if (!remoteComponent.isChannelExist(NetHelper.session())) {
            return;
        }
        User user = userComponent.getUserByUid(uid);
        if (user != null) {
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
