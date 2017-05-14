package org.okraAx.room.fy;

import org.okraAx.common.PlayerCallback;

/**
 * @author TinyZ
 * @date 2017-03-02.
 */
public class FakeUserService implements PlayerCallback {
    @Override
    public void pong() {
        System.out.println("pin : " + 99);
    }

    @Override
    public void callbackCreateRole(int ret) {

    }

    @Override
    public void callbackLogin(int ret) {

    }

    @Override
    public void callbackSyncTime(long timestamp) {

    }

    @Override
    public void registerChannel() {

    }

    @Override
    public void callbackEnterChannel(int ret) {

    }
}
