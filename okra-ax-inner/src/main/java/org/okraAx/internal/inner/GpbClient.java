/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.okraAx.internal.inner;

import com.google.protobuf.InvalidProtocolBufferException;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.NetSession;
import org.ogcs.app.Session;
import org.ogcs.netty.impl.TcpProtocolClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Google Protocol Buffer Client
 *
 * @author TinyZ
 * @since 1.0
 */
public abstract class GpbClient<O> extends TcpProtocolClient {

    private static final Logger LOG = LogManager.getLogger(GpbClient.class);
    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);
    protected static final AtomicInteger REQUEST_ID = new AtomicInteger(1000);
    protected boolean isAutoConnect;
    protected Session session;

    public GpbClient(String host, int port) {
        this(host, port, false);
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
                cp.addLast("frameEncoder", FRAME_PREPENDER);
                addCodec(cp);
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

    public abstract void addCodec(ChannelPipeline cp);

    /**
     * Invoke when channel active {@link ChannelInboundHandler#channelActive}
     *
     * @param ctx {@link ChannelHandlerContext}
     */
    public void connectionActive(ChannelHandlerContext ctx) {
        session = new NetSession(ctx);
    }

    /**
     * invoke when receive some message . See {@link SimpleChannelInboundHandler#channelRead0}
     *
     * @param ctx {@link ChannelHandlerContext}
     */
    public abstract void messageReceived(ChannelHandlerContext ctx, O msg);

    /**
     * Invoke when channel inactive {@link ChannelInboundHandler#channelInactive}
     *
     * @param ctx {@link ChannelHandlerContext}
     */
    public void connectionInactive(ChannelHandlerContext ctx) throws Exception {
        if (isAutoConnect) {
            doConnect();
        }
        session = null;
    }

    /**
     * Try reconnect to remote address.
     *
     * @return {@link ChannelFuture}
     */
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
                            LOG.info("Can't connect to remote address. on [" + host + ":" + port + "]. Try again.");
                        }, 1L, TimeUnit.SECONDS);
                    } else {
                        LOG.debug("Connect remote address [" + future.channel().remoteAddress().toString() + "] success.");
                    }
                }
            });
        }
        return future;
    }

    public boolean isAutoConnect() {
        return isAutoConnect;
    }

    public void setAutoConnect(boolean autoConnect) {
        isAutoConnect = autoConnect;
    }
}
