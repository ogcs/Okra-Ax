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
package org.okraAx.internal.v3;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ProxySingleCallback;
import org.ogcs.netty.impl.TcpProtocolClient;

import java.util.concurrent.TimeUnit;

/**
 * Proxy Client.
 * 代理客户端. 用于和服务端建立和管理{@link ProxySession}连接.
 *
 * @author TinyZ
 * @since 2.0
 */
public abstract class ProxyClient<P> extends TcpProtocolClient implements ProxySingleCallback<P> {

    private static final Logger LOG = LogManager.getLogger(ProxyClient.class);
    /**
     * 当成功建立连接后, 通过{@link #newOutputProxy()}实例化一个{@link ProxySingleCallback}.
     * notes : 在连接建立之前session值为null.
     */
    private volatile P output;
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
                if (future.isSuccess()) {
                    output = newOutputProxy();
                    //  after output proxy.
                    if (eventHandler != null) {
                        eventHandler.connected();
                    }
                    LOG.info("Connect remote address [" + future.channel().remoteAddress().toString() + "] success.");
                } else {
                    if (eventHandler != null)
                        eventHandler.connectFailed();
                    if (isAutoConnect()) {
                        future.channel().eventLoop().schedule((Runnable) () -> {
                            doConnect();
                            LOG.info("Can't connect to remote address. on [" + host + ":" + port + "]. Try again.");
                        }, 10L, TimeUnit.SECONDS);
                    }
                }
            }
        });
        return future;
    }

    public abstract P newOutputProxy();

    @Override
    public P invoker() {
        if (output == null) {

        }
        return output;
    }

    public boolean isAutoConnect() {
        return autoConnect;
    }
}
