package org.okraAx.internal.v3;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.utilities.Pair;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.05.22
 */
public class ClientContext extends ServiceContext {

    private static final Logger LOG = LogManager.getLogger(ClientContext.class);
    //
    private Bootstrap bootstrap;
    private EventLoopGroup childGroup;
    private int maxFrameLength = 102400;    //  102400 = 100k
    private int lengthFieldLength = 4;
    private int cThreadCount;
    private String host;
    private int port;
    private Map<ChannelOption<Object>, Object> options = new ConcurrentHashMap<>();
    //
    private ChannelHandler prepender;
    private List<Pair<String/* handler */, ChannelHandler>> handlers = new ArrayList<>();
    //
    private Channel channel;
    /**
     * 当连接失败(断线/连接异常)时,是否尝试重新建立连接.
     */
    private boolean autoConnect = false;

    /**
     * @param host
     * @param port
     */
    public void connect(String host, int port) {
        try {
            initialize(host, port);
            doConnect();
        } catch (Exception e) {
            stop();
            LOG.error("[ClientContext] connect remote server fail. ", e);
        } finally {
            // add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    stop();
                }
            }));
        }
    }

    public void initialize(String host, int port) {
        this.host = host;
        this.port = port;
        //
        bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        if (Epoll.isAvailable()) {
            this.childGroup = new EpollEventLoopGroup(cThreadCount);
            this.bootstrap.group(childGroup).channel(EpollServerSocketChannel.class);
        } else {
            this.childGroup = new NioEventLoopGroup(cThreadCount);
            this.bootstrap.group(childGroup).channel(NioServerSocketChannel.class);
        }
        // handlers
        this.prepender = new LengthFieldPrepender(this.lengthFieldLength, false);
        bootstrap.handler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ClientContext.this.initChannel(ch);
            }
        });
        //
        this.defaultOptions();
        if (!options.isEmpty()) {
            for (Map.Entry<ChannelOption<Object>, Object> entry : options.entrySet()) {
                bootstrap.option(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Try reconnect to remote address.
     *
     * @return {@link ChannelFuture}
     */
    public ChannelFuture doConnect() {
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    channel = future.channel();
                    LOG.info("Connect remote address [" + future.channel().remoteAddress().toString() + "] success.");
                } else {
                    LOG.error("Connect to remote address failed. [Host:" + host + ", Port:" + port + "]. Try again.");
                    if (isAutoConnect()) {
                        future.channel().eventLoop().schedule((Runnable) () -> {
                            LOG.info("Try to connect to remote address. [Host:" + host + ", Port:" + port + "]. Try again later.");
                            doConnect();
                        }, 10L, TimeUnit.SECONDS);
                    }
                }
            }
        });
        return future;
    }

    public void stop() {
        if (childGroup != null) {
            childGroup.shutdownGracefully();
            childGroup = null;
        }
        bootstrap = null;
        LOG.info("[ServerContext] stop success.");
    }

    protected void defaultOptions() {
        //  child options
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
    }

    /**
     * Init channel.
     */
    public void initChannel(Channel ch) {
        ChannelPipeline cp = ch.pipeline();
        //
        cp.addLast("frame", new LengthFieldBasedFrameDecoder(this.maxFrameLength, 0, lengthFieldLength, 0, lengthFieldLength));
        cp.addLast("prepender", this.prepender);
        //  Event Handler
        if (!handlers.isEmpty()) {
            for (Pair<String, ChannelHandler> pair : handlers) {
                cp.addLast(pair.getLeft(), pair.getRight());
            }
        }
    }

    @Override
    public <T> ClientContext registerService(T obj, Class<T> service) {
        return (ClientContext) super.registerService(obj, service);
    }

    public ClientContext setChildThread(final int threadCount) {
        this.cThreadCount = threadCount;
        return this;
    }

    /**
     * Add new netty handler after last.
     *
     * @param name    the handler's name.
     * @param handler the {@link ChannelHandler}
     */
    public ClientContext addNetHandler(final String name, final ChannelHandler handler) {
        if (name == null || handler == null) throw new NullPointerException("handler");
        this.handlers.add(Pair.of(name, handler));
        return this;
    }

    public ClientContext getMaxFrameLength(final int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
        return this;
    }

    public ClientContext setAutoConnect(final boolean autoConnect) {
        this.autoConnect = autoConnect;
        return this;
    }

    public boolean isAutoConnect() {
        return autoConnect;
    }

    public Channel channel() {
        return channel;
    }
}
