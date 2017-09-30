package org.okraAx.room.component;

import org.ogcs.app.Session;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.room.bean.RmUserInfoBean;
import org.okraAx.room.fy.RemoteUser;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.04.09
 */
@Service
public class PlayerComponent {

    private Map<String /* security code */, Long /* uid */> securityCodeMap = new ConcurrentHashMap<>();

    private Map<Long /* uid */, RmUserInfoBean> userInfoMap = new ConcurrentHashMap<>();


    private final Map<Long /* uid */, NetSession> uid2sessionMap = new ConcurrentHashMap<>();
    private final Map<NetSession, RemoteUser> session2playerMap = new ConcurrentHashMap<>();

    public RemoteUser getPlayer(NetSession session) {
        return session2playerMap.get(session);
    }

    public void playerConnected() {

    }

    public void playerDisconnected() {

    }



    public NetSession getNetSession(long uid) {
        return uid2sessionMap.get(uid);
    }


    public void registerUserInfo(RmUserInfoBean userInfo) {

    }

    public void checkPlayerSecurity(String security) {
        if (security.isEmpty()) return;
        Session session = SessionHelper.currentSession();
        if (session == null) return;


    }


}
