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

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.netty.impl.TcpProtocolClient;
import org.okraAx.internal.v3.ProxySession;

import java.util.concurrent.TimeUnit;

/**
 * Proxy Client.
 * 代理客户端. 用于和服务端建立有继承自{@link ProxySession}的连接.
 *
 * @author TinyZ
 * @since 2.0
 */
public abstract class ProxyClient<S extends ProxySession> extends TcpProtocolClient {

    private static final Logger LOG = LogManager.getLogger(ProxyClient.class);
    /**
     * 当成功建立连接后, 通过{@link #createSession()}实例化一个{@link ProxySession}.
     * notes : 在连接建立之前session值为null.
     */
    private volatile S session;
    /**
     * 当连接失败(断线/连接异常)时,是否尝试重新建立连接.
     */
    private final boolean autoConnect;
    /**
     * 连接事件回调.
     */
    private final ProxyClientEventHandler eventHandler;

    public ProxyClient(String host, int port) {
        this(host, port, false);
    }

    public ProxyClient(String host, int port, boolean autoConnect) {
        super(host, port);
        this.autoConnect = autoConnect;
        this.eventHandler = null;
    }

    public ProxyClient(String host, int port, boolean autoConnect, ProxyClientEventHandler eventHandler) {
        super(host, port);
        this.autoConnect = autoConnect;
        this.eventHandler = eventHandler;
    }

    /**
     * Try reconnect to remote address.
     *
     * @return {@link ChannelFuture}
     */
    @Override
    public ChannelFuture doConnect() {
        ChannelFuture future = super.doConnect();
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (!future.isSuccess()) {
                    if (eventHandler != null)
                        eventHandler.connectFailed();
                    if (isAutoConnect()) {
                        future.channel().eventLoop().schedule((Runnable) () -> {
                            doConnect();
                            LOG.info("Can't connect to remote address. on [" + host + ":" + port + "]. Try again.");
                        }, 10L, TimeUnit.SECONDS);
                    }
                } else {
                    session = createSession();
                    if (eventHandler != null) {
                        eventHandler.connected();
                    }
                    LOG.info("Connect remote address [" + future.channel().remoteAddress().toString() + "] success.");
                }
            }
        });
        return future;
    }

    public S session() {
        return session;
    }

    /**
     * @return An new {@link ProxySession} instance.
     */
    protected abstract S createSession();

    public boolean isAutoConnect() {
        return autoConnect;
    }
}
