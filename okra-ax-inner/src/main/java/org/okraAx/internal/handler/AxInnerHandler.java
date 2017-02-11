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

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.okraAx.internal.AxState;
import org.okraAx.component.AxConsole;
import org.okraAx.v3.OkraAx.AxInbound;
import org.okraAx.utilities.AxReplys;
import org.ogcs.netty.handler.DisruptorAdapterHandler;

/**
 * Inner handler extends {@link DisruptorAdapterHandler}.
 *
 * @author : TinyZ
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
@Sharable
public class AxInnerHandler extends DisruptorAdapterHandler<AxInbound> {

    private static final Logger LOG = LogManager.getLogger(AxInnerHandler.class);

    @Override
    protected Executor newExecutor(Session session, AxInbound axInbound) {
        return new Executor() {

            @Override
            @SuppressWarnings("unchecked")
            public void onExecute() {
                try {
                    Command command = AxConsole.INSTANCE.interpretCommand(axInbound.getCmd());
                    command.execute(session, axInbound);
                } catch (Exception e) {
                    // unknown request id and close channel.
                    session.writeAndFlush(AxReplys.error(axInbound.getRid(), AxState.STATE_1_UNKNOWN_COMMAND), ChannelFutureListener.CLOSE);
                    LOG.info("Unknown command : " + axInbound.getCmd(), e);
                }
            }

            @Override
            public void release() {
                // no-op
            }
        };
    }
}
