package org.okraAx.room.module.t1;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.google.protobuf.RpcCallback;
import com.google.protobuf.RpcController;

/**
 * @author TinyZ
 * @date 2017-02-27.
 */
public class RpcChannel implements com.google.protobuf.RpcChannel {
    @Override
    public void callMethod(Descriptors.MethodDescriptor method, RpcController controller, Message request, Message responsePrototype, RpcCallback<Message> done) {

    }
}
