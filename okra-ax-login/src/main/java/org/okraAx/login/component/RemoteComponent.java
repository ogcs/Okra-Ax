package org.okraAx.login.component;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.GameChannelService;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.utilities.NetHelper;
import org.okraAx.utilities.ProxyUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
@Service
public class RemoteComponent {

    private static final Logger LOG = LogManager.getLogger(RemoteComponent.class);

    public static final GameChannelService DEFAULT_GAME_CHANNEL_SERVICE =
            ProxyUtil.newProxyInstance(GameChannelService.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("[LoginForRoomService] Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });

    private final Map<Integer, ProxyClient<GameChannelService>> channels = new ConcurrentHashMap<>();
    private final Map<NetSession, ProxyClient<GameChannelService>> channelSessionMap = new ConcurrentHashMap<>();

    public boolean isChannelExist(NetSession session) {
        return channelSessionMap.containsKey(session);
    }

    public void onDisconnect(NetSession session) {

    }

    /**
     * 注册remote节点
     */
    public boolean verifyNode(ConnectionInfo info) {
        NetSession session = NetHelper.session();
        if (info == null) {
            LOG.error("[remote] node info is null.");
            return false;
        }
        //  密钥 -
        //  版本号

        ProxyClient<GameChannelService> channelClient = new ProxyClient<>(session, new GpbInvocationHandler(session), DEFAULT_GAME_CHANNEL_SERVICE);
        channelClient.initialize();
        channels.put(1, channelClient);


        return true;
    }

    public GameChannelService remoteClient(int id) {
        return channels.containsKey(id)
                ? channels.get(id).impl()
                : DEFAULT_GAME_CHANNEL_SERVICE;
    }


}
