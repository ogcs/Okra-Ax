package org.okraAx.room.fy;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.LoginForRoomService;
import org.okraAx.internal.v3.MtdDescUtil;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.ConnectionEventHandler;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

/**
 * room组件连接和访问logic组件的接口.
 *
 * @author TinyZ.
 * @version 2017.03.28
 * @since 2.0
 */
public final class LogicClient extends ProxyClient<LoginForRoomService> {

    private static final Logger LOG = LogManager.getLogger(LogicClient.class);
    /**
     * The fake session for real logic.
     */
    private static final LoginForRoomService EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    });
    /**
     * Is ensure sending the message to remote successful.
     */
    private final boolean ensure = false;

    public LogicClient(String host, int port) {
        super(host, port, true);
    }

    public LogicClient(String host, int port, ConnectionEventHandler eventHandler) {
        super(host, port, true, eventHandler);
    }

    @Override
    public LoginForRoomService newOutputProxy() {
        return newProxyInstance((proxy, method, args) -> {
            if (client() != null && client().isActive()) {
                writeAndFlush(MtdDescUtil.INSTANCE.pack(method, args), ensure);
            }
            return null;
        });
    }

    public LoginForRoomService logicClient() {
        if (proxy() == null || client() == null || !client().isActive())
            return EMPTY;
        return proxy();
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return null ;
    }

    /**
     * Create new proxy instance for {@link LoginForRoomService}.
     */
    private static LoginForRoomService newProxyInstance(InvocationHandler handler) {
        return (LoginForRoomService) Proxy.newProxyInstance(
                LogicClient.class.getClassLoader(),
                new Class[]{LoginForRoomService.class},
                handler);
    }

    /**
     * Send message.
     */
    private void writeAndFlush(GpcCall call, boolean ensure) {
        final Channel channel = client();
        if (call == null || channel == null || !channel.isActive())
            return;
        if (channel.isWritable()) {
            channel.writeAndFlush(call);
        } else if (ensure) {
            //  retry sending message after 1 second.
            channel.eventLoop().schedule(() -> {
                writeAndFlush(call, ensure);
            }, 1L, TimeUnit.SECONDS);
        } else {
            //  no-op
        }
    }
}
