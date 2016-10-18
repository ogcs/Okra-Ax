package org.ogcs.ax.component.exception;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public class CmdRegisteredException extends Exception {

    public CmdRegisteredException() {
    }

    public CmdRegisteredException(String message) {
        super(message);
    }

    public CmdRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CmdRegisteredException(Throwable cause) {
        super(cause);
    }

    public CmdRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
