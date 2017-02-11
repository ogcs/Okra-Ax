package org.okraAx.service;

import org.ogcs.app.Procedure;
import org.okraAx.internal.inner.axrpc.IrSession;
import org.okraAx.service.AbstractGpbService;

/**
 * @author TinyZ
 * @since 1.1
 */
public class IrCallbackService extends AbstractGpbService {

    @Procedure
    public void pong(IrSession session) {
        System.out.println("pong");
    }


}
