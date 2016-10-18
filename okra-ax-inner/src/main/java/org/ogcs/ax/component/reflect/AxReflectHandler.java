package org.ogcs.ax.component.reflect;

import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.ax.gpb.OkraAx;
import org.ogcs.netty.handler.DisruptorAdapterHandler;

/**
 * @author TinyZ
 * @date 2016-10-15.
 */
public class AxReflectHandler extends DisruptorAdapterHandler<OkraAx.AxInbound> {
    @Override
    protected Executor newExecutor(Session session, OkraAx.AxInbound msg) {
        return new Executor() {
            @Override
            public void onExecute() {




                msg.getRid();
                msg.getCmd();
                msg.getSource();
                msg.getData();

            }

            @Override
            public void release() {
                // no-op
            }
        };
    }
}
