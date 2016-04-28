package com.lj.kernel.remote.command;

import com.lj.kernel.ax.AxState;
import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.remote.Commands;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import io.netty.channel.ChannelHandler.Sharable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/22
 */
@Sharable
public class AxRemoteHandler extends DisruptorAdapterHandler<Inbound> {

    private static final Logger LOG = LogManager.getLogger(AxRemoteHandler.class);

    @Override
    protected void sessionInactive(Session session) {
        super.sessionInactive(session);
    }

    @Override
    protected Executor newExecutor(Session session, Inbound request) {
        return new Executor() {

            @Override
            @SuppressWarnings("unchecked")
            public void onExecute() {
                try {
                    Command command = Commands.INSTANCE.interpretCommand(request.getMethod());
                    command.execute(session, request);
                } catch (Exception e) {
                    session.writeAndFlush(GpbReplys.outbound(GpbReplys.error(request.getId(), AxState.STATE_1_UNKNOWN_COMMAND), request.getUid()));
                    LOG.info("Unknown command : " + request.getMethod(), e);
                }
            }

            @Override
            public void release() {
                // no-op
            }
        };
    }
}
