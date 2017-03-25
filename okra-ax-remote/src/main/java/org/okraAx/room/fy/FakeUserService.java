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
    public void callbackLogin() {

    }

    @Override
    public void callbackSyncTime(long timestamp) {

    }
}
