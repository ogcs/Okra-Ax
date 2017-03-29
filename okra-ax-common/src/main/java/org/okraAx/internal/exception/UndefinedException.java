package org.okraAx.internal.exception;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public class UndefinedException extends Exception {

    public UndefinedException() {
    }

    public UndefinedException(String message) {
        super(message);
    }

    public UndefinedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedException(Throwable cause) {
        super(cause);
    }

    public UndefinedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
