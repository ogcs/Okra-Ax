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

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.AppContext;
import org.ogcs.app.Executor;
import org.ogcs.app.NetSession;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;
import org.okraAx.internal.component.GpbMethodComponent;
import org.okraAx.v3.GpcCall;

/**
 * Inner handler extends {@link DisruptorAdapterHandler}.
 *
 * @author : TinyZ
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
@Sharable
public class GpbProxyHandler extends DisruptorAdapterHandler<GpcCall> {

    private static final Logger LOG = LogManager.getLogger(GpbProxyHandler.class);

    private GpbMethodComponent methodComponent = AppContext.getBean(GpbMethodComponent.class);

    @Override
    protected Executor newExecutor(Session session, GpcCall call) {
        return new Executor() {

            @Override
//            @SuppressWarnings("unchecked")
            public void onExecute() {
                try {
                    GpbCommand command = methodComponent.interpret(call.getMethod());
                    //  TODO:command是否允许外部访问


                    command.execute(session, call);
                } catch (Exception e) {
                    LOG.info("Unknown command : " + call.getMethod(), e);
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

    @Override
    protected Session newSession(Channel channel) {
        return new NetSession(channel);
    }
}
