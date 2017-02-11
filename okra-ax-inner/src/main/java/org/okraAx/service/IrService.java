package org.okraAx.service;

import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.Procedure;
import org.ogcs.event.EventDispatcher;
import org.ogcs.event.MultiListenerEventDispatcher;
import org.okraAx.internal.bean.AxCoInfo;
import org.okraAx.internal.component.GpbServiceComponent;
import org.okraAx.internal.inner.axrpc.IrClient;
import org.okraAx.internal.inner.axrpc.IrSession;
import org.okraAx.v3.bean.ReqAuth;

/**
 * @author TinyZ
 * @since 1.1
 */
public class IrService extends AbstractGpbService {

    private GpbServiceComponent serviceManager = AppContext.getBean(GpbServiceComponent.class);

    private EventDispatcher dispatcher = new MultiListenerEventDispatcher();

    @Procedure
    public void ping(IrSession session) {
        System.out.println("ping");
        session.push(0, 13001);
    }

    @Procedure
    public void register(IrSession session, String key) {
        System.out.println("Key : " + key);
        session.push(0, 12002, ReqAuth.newBuilder()
                .setKey(key)
                .build());
    }

    public void reigsterComponent(IrSession session, AxCoInfo info) {
        IrClient client = new IrClient(info.getHost(), info.getPort());
        client.start();
        IrSession netSession = new IrSession(client.client());
//        netSession.push();

//        channel.writeAndFlush()

//        AxRpcHandler axRpcHandler = channel.pipeline().get(AxRpcHandler.class);

//        IrSession session1 = new IrSession()
//        client

        dispatcher.dispatchEvent(null, null, info);
    }














}
