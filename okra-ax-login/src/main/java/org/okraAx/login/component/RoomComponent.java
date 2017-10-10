package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.GameChannelService;
import org.okraAx.common.RoomService;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbProxyUtil;
import org.okraAx.login.bean.ChannelInfo;
import org.okraAx.login.server.User;
import org.okraAx.utilities.NetHelper;
import org.okraAx.utilities.ProxyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间组件. 管理系统下全部频道房间.
 *
 * @author TinyZ.
 * @version 2017.08.26
 */
@Component
public final class RoomComponent {

    private static final Logger LOG = LogManager.getLogger(RoomComponent.class);

    public static final RoomService DEFAULT_ROOM_SERVICE =
            ProxyUtil.newProxyInstance(RoomService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[RoomService] Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });

    @Autowired
    private UserComponent userComponent;

    /**
     * 频道列表
     */
    private Map<Integer, ProxyClient<RoomService>> maps = new ConcurrentHashMap<>();
    private Map<NetSession, ProxyClient<RoomService>> roomSessionMap = new ConcurrentHashMap<>();

    public ProxyClient<RoomService> getRoomBySession(NetSession session) {
        return roomSessionMap.get(session);
    }

    /**
     * 注册频道
     *
     * @param security 安全码
     * @param version  版本号
     * @param roomId   房间进程唯一ID
     * @param type     房间类型
     */
    public void registerChannel(String security, long version, int roomId, int type, String host, int port) {
        NetSession session = NetHelper.session();
        if (session == null || !session.isActive()) return;
        //  TODO: 验证安全码 和 版本号
        ChannelInfo channelInfo = new ChannelInfo(roomId, type, version, host, port);
        try {
            ProxyClient<RoomService> roomClient = GpbProxyUtil.newProxyClient(session, DEFAULT_ROOM_SERVICE);
            //
            maps.put(roomId, roomClient);
            roomClient.impl().callbackRegister(0);
        } catch (Exception e) {
            session.close();
            LOG.info("[S] register channel fail. info:" + channelInfo.toString(), e);
        }
    }

    public void roomDisconnect(NetSession session) {
        ProxyClient<RoomService> client = roomSessionMap.remove(session);
        if (client != null && client.isActive()) {
            client.getSession().close();
        }
    }

    public void unregisterChannel(int roomId) {
        ProxyClient<RoomService> roomClient = maps.remove(roomId);
        if (roomClient != null) {
            roomClient.getSession().close();
        }
    }

    public void enterChannel(int roomId) {
        User user = userComponent.getUserBySession(NetHelper.session());
        if (user == null) return;

        //  TODO:检查是否已经进入房间


        ProxyClient<RoomService> roomClient = maps.get(roomId);
        if (roomClient != null) {

            //  TODO: 申请进入频道
            roomClient.impl().enterChannel();
        }
    }

}
