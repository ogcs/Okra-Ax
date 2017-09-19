package org.okraAx.internal.v3.protobuf;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.concurrent.ConcurrentEvent;
import org.ogcs.concurrent.ConcurrentEventFactory;
import org.ogcs.concurrent.ConcurrentHandler;
import org.ogcs.netty.handler.DisruptorAdapterBy41xHandler;
import org.okraAx.internal.net.NetSession;
import org.okraAx.internal.v3.ConnectionEventHandler;
import org.okraAx.internal.v3.ServerContext;
import org.okraAx.internal.v3.ServiceContext;
import org.okraAx.utilities.NetHelper;
import org.okraAx.v3.GpcCall;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static io.netty.channel.ChannelHandler.Sharable;

/**
 * @author TinyZ.
 * @version 2017.05.08
 */
@Sharable
public class GpcEventDispatcher extends SimpleChannelInboundHandler<GpcCall> {

    private static final Logger LOG = LogManager.getLogger(GpcEventDispatcher.class);

    private static final Map<ChannelId, NetSession> NET_SESSION_MAP = new ConcurrentHashMap<>();
    private static final int DEFAULT_RING_BUFFER_SIZE = 8 * 1024;
    private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();
    private static final ThreadLocal<Disruptor<ConcurrentEvent>> THREAD_LOCAL = new ThreadLocal<Disruptor<ConcurrentEvent>>() {
        @Override
        protected Disruptor<ConcurrentEvent> initialValue() {
            Disruptor<ConcurrentEvent> disruptor = new Disruptor<>(
                    ConcurrentEventFactory.DEFAULT, DEFAULT_RING_BUFFER_SIZE, CACHED_THREAD_POOL, ProducerType.SINGLE, new BlockingWaitStrategy());
            disruptor.handleEventsWith(new ConcurrentHandler());
//            disruptor.handleExceptionsWith();
            disruptor.start();
            return disruptor;
        }
    };

    private final ServiceContext context;

    private ConnectionEventHandler handler;

    public GpcEventDispatcher(ServiceContext context) {
        this.context = context;
    }

    public GpcEventDispatcher(ServiceContext context, ConnectionEventHandler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        NetSession session = new NetSession(ctx.channel());
        NET_SESSION_MAP.put(ctx.channel().id(), session);
        if (handler != null) {
            try {
                NetHelper.setSession(session);
                handler.connected();
            } finally {
                NetHelper.setSession(null);
            }
        }
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GpcCall msg) throws Exception {
        NetSession session = NET_SESSION_MAP.get(ctx.channel().id());
        if (null == session)
            return;
        RingBuffer<ConcurrentEvent> ringBuffer = THREAD_LOCAL.get().getRingBuffer();
        long next = ringBuffer.next();
        try {
            ConcurrentEvent commandEvent = ringBuffer.get(next);
            commandEvent.setValues(newExecutor(session, msg));
        } finally {
            ringBuffer.publish(next);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NetSession session = NET_SESSION_MAP.remove(ctx.channel().id());
        if (null != session) {
            if (handler != null) {
                try {
                    NetHelper.setSession(session);
                    handler.disconnected();
                } finally {
                    NetHelper.setSession(null);
                }
            }
            session.close();
        }
        super.channelInactive(ctx);
    }

    protected Executor newExecutor(NetSession session, GpcCall msg) {
        return new SimpleExecutor(context, msg, session);
    }

    private class SimpleExecutor implements Executor {

        private final ServiceContext context;
        private final GpcCall msg;
        private final NetSession session;

        public SimpleExecutor(ServiceContext context, GpcCall call, NetSession session) {
            this.context = context;
            this.msg = call;
            this.session = session;
        }

        @Override
        public void release() {

        }

        @Override
        public void onExecute() {
            try {
                Command command = context.getMethod(msg.getMethod());
                command.execute(session, msg);
            } catch (Exception e) {
                LOG.info("[GpcEventDispatcher] GpcCall execute error.api:{} ", msg.getMethod(), e);
            }
        }
    }
}
