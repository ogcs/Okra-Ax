package org.okraAx.logic.component;

import org.ogcs.app.AppContext;
import org.ogcs.app.Procedure;
import org.okraAx.internal.inner.axrpc.IrSession;
import org.okraAx.internal.v3.AbstractGpbService;

/**
 * 逻辑进程对外公开的服务
 * @author TinyZ
 * @date 2017-02-11.
 */
public class LogicForPublicService extends AbstractGpbService {

    private PlayerComponent pc = AppContext.getBean(PlayerComponent.class);

    public LogicForPublicService() {
        super(descriptor);
    }

    @Procedure
    public void sayHello(IrSession session, String msg) {

//        session.push();
    }


}
