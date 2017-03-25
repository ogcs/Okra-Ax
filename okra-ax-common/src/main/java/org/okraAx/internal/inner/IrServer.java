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

import io.netty.channel.ChannelHandler;
import org.ogcs.netty.impl.TcpProtocolServer;
import org.okraAx.internal.core.AxComponent;
import org.okraAx.internal.v3.FyChannelInitializer;

/**
 * 内部组件 - Server模块
 *
 * @author : TinyZ
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public class IrServer extends TcpProtocolServer implements AxComponent {

    private String id;

    public IrServer(String id, int port) {
        this.id = id;
        this.port = port;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new FyChannelInitializer();
    }
}
