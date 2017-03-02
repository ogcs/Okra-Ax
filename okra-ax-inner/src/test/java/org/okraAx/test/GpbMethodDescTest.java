package org.okraAx.test;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.junit.Test;
import org.okraAx.internal.v3.GpbMethodDesc;
import org.okraAx.v3.chess.services.FyChessSi;
import org.okraAx.v3.room.beans.FyRoomMi;
import org.okraAx.v3.room.beans.MsgEnterRoom;

/**
 * @author TinyZ
 * @date 2017-02-28.
 */
public class GpbMethodDescTest {


    public static void main(String[] args) throws InvalidProtocolBufferException {

    }

    @Test
    public void testUnpack() throws InvalidProtocolBufferException {
        for (Descriptors.ServiceDescriptor serviceDescriptor : FyChessSi.getDescriptor().getServices()) {
            MethodDescriptor methodByName = serviceDescriptor.findMethodByName("onEnterRoom");
            if (methodByName != null) {
                GpbMethodDesc method = new GpbMethodDesc(methodByName);
                //
                MsgEnterRoom message = MsgEnterRoom.newBuilder()
                        .setName("xx")
                        .setRoomId(999)
                        .setSeat(8)
                        .setUid(10001)
                        .build();
                Message unpack = method.unpack(message.toByteString());
                Message pack = method.pack(new Object[]{
                        999, 8, 10001L, "xx"
                });
                System.out.println();

            }
        }


    }

}
