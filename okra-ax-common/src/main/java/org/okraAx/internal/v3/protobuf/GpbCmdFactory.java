package org.okraAx.internal.v3.protobuf;

import org.ogcs.app.Command;
import org.okraAx.internal.v3.CmdFactory;

import java.lang.reflect.Method;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
public class GpbCmdFactory implements CmdFactory {

    private final GpbMessageContext context;

    public GpbCmdFactory(GpbMessageContext context) {
        this.context = context;
    }

    @Override
    public Command newCommand(Object obj, Method method) {
        return new GpbCommand(obj, method, context);
    }

}
