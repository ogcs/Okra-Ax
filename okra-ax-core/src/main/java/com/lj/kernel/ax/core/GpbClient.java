package com.lj.kernel.ax.core;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lj.kernel.ax.HandlerConst;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.DefaultSession;
import org.ogcs.app.Session;
import org.ogcs.netty.impl.TcpProtocolClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public abstract class GpbClient<O> extends TcpProtocolClient {

    private static final Logger LOG = LogManager.getLogger(GpbClient.class);

    protected static final AtomicInteger REQUEST_ID = new AtomicInteger(1000);

    protected boolean isAutoConnect = false;
    protected Session session;

    public GpbClient(String host, int port) {
        super(host, port);
    }

    public GpbClient(String host, int port, boolean isAutoConnect) {
        super(host, port);
        this.isAutoConnect = isAutoConnect;
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                cp.addLast("frameEncoder", HandlerConst.FRAME_PREPENDER);

                addGpbDecoder(cp);
                cp.addLast("gpbEncoder", HandlerConst.GPB_ENCODER);

                cp.addLast("handler", new SimpleChannelInboundHandler<O>() {

                    @Override
                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                        connectionActive(ctx);
                        super.channelActive(ctx);
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, O msg) throws Exception {
                        messageReceived(ctx, msg);
                    }

                    @Override
                    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                        connectionInactive(ctx);
                        super.channelInactive(ctx);
                    }

                    @Override
                    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                        if (cause.getCause() instanceof InvalidProtocolBufferException) {
                            LOG.warn("Invalid Protocol Buffer Message. ", cause);
                        } else if (cause instanceof IOException) {
                            LOG.info("远程主机强迫关闭了一个现有的连接. Channel" + ctx.channel().remoteAddress().toString());
                        } else {
                            LOG.info("Exception : ", cause);
                            super.exceptionCaught(ctx, cause);
                        }
                    }
                });
            }
        };
    }

    public Session session() {
        return session;
    }

    public abstract void addGpbDecoder(ChannelPipeline cp);

    public void connectionActive(ChannelHandlerContext ctx) {
        session = new DefaultSession(ctx);
    }

    public abstract void messageReceived(ChannelHandlerContext ctx, O msg);

    public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
        if (isAutoConnect) {
            doConnect();
        }
        session = null;
    }

    @Override
    public ChannelFuture doConnect() {
        ChannelFuture future = super.doConnect();
        if (isAutoConnect) {
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (!future.isSuccess()) {
                        future.channel().eventLoop().schedule((Runnable) () -> {
                            doConnect();
                            LOG.debug("Can't connect to remote address. on [" + host + ":" + port + "]. Try again.");
                        }, 1L, TimeUnit.SECONDS);
                    } else {
                        LOG.debug("Connect remote address [" + future.channel().remoteAddress().toString() + "] success.");
                    }
                }
            });
        }
        return future;
    }

}
