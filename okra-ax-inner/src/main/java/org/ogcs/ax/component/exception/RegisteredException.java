package org.ogcs.ax.component.exception;

/**
 * @author TinyZ
 * @date 2016-10-16.
 */
public class RegisteredException extends Exception {

    public RegisteredException(String message) {
        super(message);
    }

    public RegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RegisteredException(Throwable cause) {
        super(cause);
    }

    public RegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
