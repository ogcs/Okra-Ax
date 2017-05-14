package org.okraAx.common;

/**
 * @author TinyZ.
 * @version 2017.03.12
 */
public interface LoginCallback {

    void callbackCreateRole(int ret);
    void callbackLogin(int ret);
    void callbackSyncTime(long timestamp);
}
