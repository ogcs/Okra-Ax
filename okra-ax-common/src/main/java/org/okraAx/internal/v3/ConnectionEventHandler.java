package org.okraAx.internal.v3;

/**
 * @author TinyZ.
 * @version 2017.03.28
 */
public interface ConnectionEventHandler {

    void connected();

    void connectFailed();

    void disconnected();

}
