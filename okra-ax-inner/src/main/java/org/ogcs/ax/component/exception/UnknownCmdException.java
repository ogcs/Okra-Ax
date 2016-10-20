package org.ogcs.ax.component.exception;

/**
 * @author TinyZ
 * @date 2016-10-20.
 */
public class UnknownCmdException extends Exception {

    public UnknownCmdException(String message) {
        super(message);
    }

    public UnknownCmdException(String message, Throwable cause) {
        super(message, cause);
    }
}
