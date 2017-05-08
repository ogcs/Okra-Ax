package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.NetSession;
import org.okraAx.login.bean.ChannelInfo;
import org.okraAx.login.mybatis.RoomClient;
import org.okraAx.login.mybatis.UserClient;
import org.okraAx.utilities.SessionHelper;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间组件. 管理系统下全部频道房间.
 *
 * @author TinyZ.
 * @version 2017.03.26
 */
@Component
public final class RoomComponent {

    private static final Logger LOG = LogManager.getLogger(RoomComponent.class);
    /**
     * 频道列表
     */
    private Map<Integer, RoomClient> maps = new ConcurrentHashMap<>();

    /**
     * 注册频道
     *
     * @param security 安全码
     * @param version  版本号
     * @param roomId   房间进程唯一ID
     * @param type     房间类型
     */
    public void registerChannel(String security, long version, int roomId, int type, String host, int port) {
        NetSession session = SessionHelper.currentSession();
        if (session == null || !session.isActive()) return;
        //  TODO: 验证安全码 和 版本号
        ChannelInfo channelInfo = new ChannelInfo(roomId, type, version, host, port);
        try {
            RoomClient roomClient = new RoomClient(channelInfo);
            roomClient.setSession(session);
            session.setConnector(roomClient);  //
            //
            maps.put(roomId, roomClient);
            roomClient.roomClient().callbackRegister(0);
        } catch (Exception e) {
            session.close();
            LOG.info("[S] register channel fail. info:" + channelInfo.toString(), e);
        }
    }

    public void unregisterChannel(int roomId) {
        RoomClient roomClient = maps.remove(roomId);
        if (roomClient != null) {
            roomClient.session().close();
        }
    }

    public void enterChannel(int roomId) {
        UserClient user = SessionHelper.curPlayer();
        if (user == null) return;

        //  TODO:检查是否已经进入房间


        RoomClient roomClient = maps.get(roomId);
        if (roomClient != null) {

            //  TODO: 申请进入频道
            roomClient.roomClient().enterChannel();
        }
    }

}
