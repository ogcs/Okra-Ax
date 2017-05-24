package org.okraAx.internal.v3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.utilities.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.04.28
 */
public class ServerContext extends ServiceContext {

    private static final Logger LOG = LogManager.getLogger(ServerContext.class);

    //  Netty
    protected volatile ServerBootstrap bootstrap;
    protected volatile EventLoopGroup parentGroup;
    protected volatile EventLoopGroup childGroup;
    private int maxFrameLength = 102400;    //  102400 = 100k
    private int lengthFieldLength = 4;
    private int pThreadCount;
    private int cThreadCount;
    private Map<ChannelOption<Object>, Object> options = new ConcurrentHashMap<>();
    //
    private ChannelHandler prepender;
    private List<Pair<String/* handler */, ChannelHandler>> handlers = new ArrayList<>();
    //
    protected Channel channel;

    public void start(int port) {
        try {
            initialize(pThreadCount, cThreadCount, options);
            ChannelFuture future = this.bootstrap.bind(port).sync();
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {

                        channel = future.channel();
                    } else {

                    }
                }
            });
        } catch (InterruptedException e) {
            LOG.error("[ServerContext] start failed. ", e);
            stop();// shutdown
        } finally {
            // add shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
                @Override
                public void run() {
                    stop();
                }
            }));
            LOG.info("[ServerContext] start success.");
        }
    }

    public void stop() {
        if (parentGroup != null) {
            parentGroup.shutdownGracefully();
            parentGroup = null;
        }
        if (childGroup != null) {
            childGroup.shutdownGracefully();
            childGroup = null;
        }
        bootstrap = null;
        LOG.info("[ServerContext] stop success.");
    }

    /**
     * 初始化
     *
     * @param pThreadCount parent thread count.
     * @param cThreadCount worker thread count.
     * @param options      netty network options。
     */
    public void initialize(int pThreadCount, int cThreadCount,
                           Map<ChannelOption<Object>, Object> options) {
        this.bootstrap = new ServerBootstrap();
        if (Epoll.isAvailable()) {
            this.parentGroup = new EpollEventLoopGroup(pThreadCount);
            this.childGroup = new EpollEventLoopGroup(cThreadCount);
            this.bootstrap.group(parentGroup, childGroup).channel(EpollServerSocketChannel.class);
        } else {
            this.parentGroup = new NioEventLoopGroup(pThreadCount);
            this.childGroup = new NioEventLoopGroup(cThreadCount);
            this.bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class);
        }
        // handlers
        this.prepender = new LengthFieldPrepender(this.lengthFieldLength, false);
        bootstrap.childHandler(new ChannelInitializer() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ServerContext.this.initChannel(ch);
            }
        });
        //
        this.defaultOptions();
        if (!options.isEmpty()) {
            for (Map.Entry<ChannelOption<Object>, Object> entry : options.entrySet()) {
                bootstrap.childOption(entry.getKey(), entry.getValue());
            }
        }
    }

    protected void defaultOptions() {
        //  parent options
        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        //  child options
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
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
    public <T> ServerContext registerService(T obj, Class<T> service) {
        return (ServerContext) super.registerService(obj, service);
    }

    /**
     * Add new netty handler after last.
     *
     * @param name    the handler's name.
     * @param handler the {@link ChannelHandler}
     */
    public ServerContext addNetHandler(final String name, final ChannelHandler handler) {
        if (name == null || handler == null) throw new NullPointerException("handler");
        this.handlers.add(Pair.of(name, handler));
        return this;
    }

    public ServerContext setParentThread(final int threadCount) {
        this.pThreadCount = threadCount;
        return this;
    }

    public ServerContext setChildThread(final int threadCount) {
        this.cThreadCount = threadCount;
        return this;
    }

    public ServerContext getMaxFrameLength(final int maxFrameLength) {
        this.maxFrameLength = maxFrameLength;
        return this;
    }

    public ServerContext setLengthFieldLength(final int lengthFieldLength) {
        this.lengthFieldLength = lengthFieldLength;
        return this;
    }
}