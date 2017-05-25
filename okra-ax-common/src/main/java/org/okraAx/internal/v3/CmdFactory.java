package org.okraAx.internal.v3;

import org.ogcs.app.Command;

import java.lang.reflect.Method;

/**
 * @author TinyZ.
 * @version 2017.05.21
 */
public interface CmdFactory {

    /**
     * Create new {@link Command} instance.
     *
     * @param obj    the obj instance.
     * @param method the method
     * @return return new {@link Command} instance.
     */
    Command newCommand(Object obj, Method method);
}
