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
package org.okraAx.internal.handler;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;
import org.okraAx.v3.Rpc;
import org.okraAx.internal.component.GpbServiceComponent;

/**
 * Inner handler extends {@link DisruptorAdapterHandler}.
 *
 * @author : TinyZ
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
@Sharable
public class AxRpcHandler extends DisruptorAdapterHandler<Rpc> {

    private static final Logger LOG = LogManager.getLogger(AxRpcHandler.class);

    private GpbServiceComponent serviceManager = AppContext.getBean(GpbServiceComponent.class);

    @Override
    protected Executor newExecutor(Session session, Rpc rpc) {
        return new Executor() {

            @Override
            @SuppressWarnings("unchecked")
            public void onExecute() {
                try {
                    Command command = serviceManager.interpret(rpc.getApi());
                    command.execute(session, rpc);
                } catch (Exception e) {
                    LOG.info("Unknown command : " + rpc.getApi(), e);
                }
            }

            @Override
            public void release() {
                // no-op
            }
        };
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }
}
