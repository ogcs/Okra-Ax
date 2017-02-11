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

import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.ogcs.netty.impl.TcpProtocolClient;

import java.util.concurrent.TimeUnit;

/**
 * Auto Reconnect Client
 *
 * @author TinyZ
 * @since 1.0
 */
public abstract class AutoClient extends TcpProtocolClient {

    private static final Logger LOG = LogManager.getLogger(AutoClient.class);
    protected boolean isAutoConnect;
    protected Session session;

    public AutoClient(String host, int port) {
        this(host, port, false);
    }

    public AutoClient(String host, int port, boolean isAutoConnect) {
        super(host, port);
        this.isAutoConnect = isAutoConnect;
    }

    public Session session() {
        return session;
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
