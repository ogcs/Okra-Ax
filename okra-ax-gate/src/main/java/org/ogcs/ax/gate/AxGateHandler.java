package org.ogcs.ax.gate;

import org.ogcs.GpbReplys;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.ax.config.AxState;
import org.ogcs.ax.gpb3.GpbD.Request;
import org.ogcs.netty.handler.DisruptorAdapterHandler;


/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/22
 */
@Sharable
public class AxGateHandler extends DisruptorAdapterHandler<Request> {

    private static final Logger LOG = LogManager.getLogger(AxGateHandler.class);

    @Override
    protected Executor newExecutor(Session session, Request request) {
        return new Executor() {

            @Override
            @SuppressWarnings("unchecked")
            public void onExecute() {
                if (null == request) {
                    throw new NullPointerException("request");
                }
                if (!isLogin(session) && !GateCommands.INSTANCE.isCmdWithoutAuth(request.getCmd())) {
                    session.writeAndFlush(GpbReplys.error(-1, AxState.STATE_1_UNKNOWN_COMMAND), ChannelFutureListener.CLOSE);
                    return;
                }
                try {
                    Command command = GateCommands.INSTANCE.interpretCommand(request.getCmd());
                    command.execute(session, request);
                } catch (Exception e) {
                    // unknown request id and close channel.
                    session.writeAndFlush(GpbReplys.error(-1, AxState.STATE_1_UNKNOWN_COMMAND), ChannelFutureListener.CLOSE);
                    LOG.info("Unknown command : " + request.getCmd(), e);
                }
            }

            private boolean isLogin(Session session) {
                return session.getConnector() != null;
            }

            @Override
            public void release() {
                // no-op
            }
        };
    }
}
