package org.okraAx.room.fy;

import io.netty.channel.ChannelHandler;
import org.okraAx.common.LogicForRoomService;
import org.okraAx.internal.inner.ProxyClient;
import org.okraAx.internal.v3.GpbChannelInitializer;
import org.okraAx.internal.v3.ProxySession;

/**
 * The room client. auto reconnect interval 1 sec.
 *
 * @author TinyZ.
 * @version 2017.03.28
 */
public class LogicClient extends ProxyClient<ProxySession<LogicForRoomService>> {

    /**
     * The fake session for real logic.
     */
    private static final ProxySession<LogicForRoomService> EMPTY =
            new ProxySession<>(null, LogicForRoomService.class);

    public LogicClient(String host, int port) {
        super(host, port, true);
    }

    public LogicForRoomService logicClient() {
        ProxySession<LogicForRoomService> session = session();
        if (session == null || !session.isActive()) return EMPTY.invoker();
        return session.invoker();
    }

    @Override
    protected ProxySession<LogicForRoomService> createSession() {
        return new ProxySession<>(client(), LogicForRoomService.class);
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new GpbChannelInitializer();
    }
}
