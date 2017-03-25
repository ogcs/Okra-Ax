package org.okraAx.room.fy;

import com.google.protobuf.Descriptors;
import org.okraAx.common.PlayerRoomCallback;
import org.okraAx.internal.v3.AbstractGpbService;

/**
 * @author TinyZ.
 * @version 2017.03.17
 */
public class FakePlayerCallback extends AbstractGpbService implements PlayerRoomCallback {
    @Override
    public void pong() {

    }

    @Override
    public Descriptors.ServiceDescriptor desc() {
        return null;
    }
}
