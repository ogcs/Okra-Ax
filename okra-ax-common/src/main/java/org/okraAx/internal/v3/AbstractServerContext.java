//package org.okraAx.internal.v3;
//
//import com.google.protobuf.ByteString;
//import com.google.protobuf.Descriptors.FileDescriptor;
//import com.google.protobuf.Descriptors.MethodDescriptor;
//import com.google.protobuf.Descriptors.ServiceDescriptor;
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.google.protobuf.Message;
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.*;
//import io.netty.channel.epoll.Epoll;
//import io.netty.channel.epoll.EpollEventLoopGroup;
//import io.netty.channel.epoll.EpollServerSocketChannel;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
//import io.netty.handler.codec.LengthFieldPrepender;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.ogcs.app.Command;
//import org.okraAx.v3.GpcCall;
//
//import java.lang.reflect.Method;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author TinyZ.
// * @version 2017.05.10
// */
//public class AbstractServerContext {
//
//    private static final Logger LOG = LogManager.getLogger(AbstractServerContext.class);
//
//    //  Netty
//    protected volatile ServerBootstrap bootstrap;
//    protected volatile EventLoopGroup parentGroup;
//    protected volatile EventLoopGroup childGroup;
//    private int maxFrameLength = 102400;    //  102400 = 100k
//    private int lengthFieldLength = 4;
//    private int pThreadCount;
//    private int cThreadCount;
//    private Map<ChannelOption<Object>, Object> options = new ConcurrentHashMap<>();
//    /**
//     * 序列化编解码工具
//     */
//    private ChannelHandler codec;
//    private ChannelHandler prepender;
//    private ChannelHandler dispatcher;
//    //
//    protected Channel channel;
//
//    //  Google protocol buffers
//    private final Map<String, Command> methods = new ConcurrentHashMap<>();
//
//    public void start(int port) {
//        try {
//            initialize(pThreadCount, cThreadCount, options);
//            ChannelFuture future = this.bootstrap.bind(port).sync();
//            future.addListener(new ChannelFutureListener() {
//                @Override
//                public void operationComplete(ChannelFuture future) throws Exception {
//                    if (future.isSuccess()) {
//
//                        channel = future.channel();
//                    } else {
//
//                    }
//                }
//            });
//        } catch (InterruptedException e) {
//            LOG.error("[ServerContext] start failed. ", e);
//            stop();// shutdown
//        } finally {
//            // add shutdown hook
//            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    stop();
//                }
//            }));
//            LOG.info("[ServerContext] start success.");
//        }
//    }
//
//    public void stop() {
//        if (parentGroup != null) {
//            parentGroup.shutdownGracefully();
//            parentGroup = null;
//        }
//        if (childGroup != null) {
//            childGroup.shutdownGracefully();
//            childGroup = null;
//        }
//        bootstrap = null;
//        LOG.info("[ServerContext] stop success.");
//    }
//
//    /**
//     * 初始化
//     *
//     * @param pThreadCount parent thread count.
//     * @param cThreadCount worker thread count.
//     * @param options      netty network options。
//     */
//    public void initialize(int pThreadCount, int cThreadCount,
//                           Map<ChannelOption<Object>, Object> options) {
//        if (this.codec == null) {
//            LOG.error("[GpbServerContext] the serialize/deserialize handler is null.");
//            throw new NullPointerException("codec");
//        }
//        this.bootstrap = new ServerBootstrap();
//        if (Epoll.isAvailable()) {
//            this.parentGroup = new EpollEventLoopGroup(pThreadCount);
//            this.childGroup = new EpollEventLoopGroup(cThreadCount);
//            this.bootstrap.group(parentGroup, childGroup).channel(EpollServerSocketChannel.class);
//        } else {
//            this.parentGroup = new NioEventLoopGroup(pThreadCount);
//            this.childGroup = new NioEventLoopGroup(cThreadCount);
//            this.bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class);
//        }
//        //
//        this.prepender = new LengthFieldPrepender(this.lengthFieldLength, false);
//        this.dispatcher = new NetEventDispatcher(this);
//        bootstrap.childHandler(new ChannelInitializer() {
//            @Override
//            protected void initChannel(Channel ch) throws Exception {
//                AbstractServerContext.this.initChannel(ch);
//            }
//        });
//        //
//        this.defaultOptions();
//        if (!options.isEmpty()) {
//            for (Map.Entry<ChannelOption<Object>, Object> entry : options.entrySet()) {
//                bootstrap.childOption(entry.getKey(), entry.getValue());
//            }
//        }
//    }
//
//    protected void defaultOptions() {
//        //  parent options
//        bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
//        //  child options
//        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
//        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
//        bootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//    }
//
//    /**
//     * Init channel.
//     */
//    public void initChannel(Channel ch) {
//        ChannelPipeline cp = ch.pipeline();
//        //
//        cp.addLast("frame", new LengthFieldBasedFrameDecoder(this.maxFrameLength, 0, lengthFieldLength, 0, lengthFieldLength));
//        cp.addLast("prepender", this.prepender);
//        //  Serialize / Deserialize
//        cp.addLast("codec", this.codec);
//        //  Event Dispatcher
//        cp.addLast("handler", this.dispatcher);
//    }
//
//    public <T> void registerService(T obj, Class<T> service) {
//        if (obj == null) throw new NullPointerException("obj");
//        if (service == null) throw new NullPointerException("service");
//        for (Method method : service.getDeclaredMethods()) {
//            if (methods.containsKey(method.getName())) {
//                LOG.info("[ServerContext] the method name is registered. method name : {}", method.getName());
//            }
//            Command command = new Command(obj, method, this);
//            methods.put(method.getName(), command);
//        }
//    }
//
//    protected Command createCommand() {
//
//    }
//
//    public GpbCommand getMethod(String methodName) {
//        return methods.get(methodName);
//    }
//
//    public void setpThreadCount(int pThreadCount) {
//        this.pThreadCount = pThreadCount;
//    }
//
//    public void setcThreadCount(int cThreadCount) {
//        this.cThreadCount = cThreadCount;
//    }
//
//    public int getpThreadCount() {
//        return pThreadCount;
//    }
//
//    public int getcThreadCount() {
//        return cThreadCount;
//    }
//
//    public void putChannelOptions(ChannelOption<Object> key, Object value) {
//        options.put(key, value);
//    }
//
//    public int getMaxFrameLength() {
//        return maxFrameLength;
//    }
//
//    public void setMaxFrameLength(int maxFrameLength) {
//        this.maxFrameLength = maxFrameLength;
//    }
//
//    public int getLengthFieldLength() {
//        return lengthFieldLength;
//    }
//
//    public void setLengthFieldLength(int lengthFieldLength) {
//        this.lengthFieldLength = lengthFieldLength;
//    }
//
//    public ChannelHandler getCodec() {
//        return codec;
//    }
//
//    public void setCodec(ChannelHandler codec) {
//        this.codec = codec;
//    }
//}
