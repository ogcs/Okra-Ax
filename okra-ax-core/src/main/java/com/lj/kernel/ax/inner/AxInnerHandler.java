package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.GpbReplys;
import com.lj.kernel.gpb.OkraAx;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Command;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;

import static com.lj.kernel.ax.AxState.STATE_1_UNKNOWN_COMMAND;


/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/22
 */
@Sharable
public class AxInnerHandler extends DisruptorAdapterHandler<AxInbound> {

    private static final Logger LOG = LogManager.getLogger(AxInnerHandler.class);

    @Override
    protected Executor newExecutor(Session session, AxInbound axRequest) {
        return new Executor() {

            @Override
            @SuppressWarnings("unchecked")
            public void onExecute() {
                try {
                    Command command = AxCommands.INSTANCE.interpretCommand(axRequest.getCmd());
                    command.execute(session, axRequest);
                } catch (Exception e) {
                    // unknown request id and close channel.

                    OkraAx.AxOutbound.newBuilder()
                            .setData(null)
                            .addTarget(axRequest.getSource())
                            .build();

                    session.writeAndFlush(GpbReplys.error(-1, STATE_1_UNKNOWN_COMMAND), ChannelFutureListener.CLOSE);
                    LOG.info("Unknown command : " + axRequest.getCmd(), e);
                }
            }

            @Override
            public void release() {
                // no-op
            }
        };
    }
}
