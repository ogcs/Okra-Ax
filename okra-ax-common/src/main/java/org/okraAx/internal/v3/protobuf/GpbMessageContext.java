package org.okraAx.internal.v3.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.v3.GpcCall;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author TinyZ.
 * @version 2017.04.28
 */
@Service
public class GpbMessageContext {

    private static final Logger LOG = LogManager.getLogger(GpbMessageContext.class);

    //  Google protocol buffers
    private final Map<String /* method */, String /* message */> methodInputTypeMap = new ConcurrentHashMap<>();
    private final Map<String /* message */, GpbMessageDesc> gpbMsgDescMap = new ConcurrentHashMap<>();

    public void registerGpbMsgDesc(FileDescriptor fileDescriptor) {
        if (fileDescriptor == null) return;
        //  service
        for (ServiceDescriptor service : fileDescriptor.getServices()) {
            for (MethodDescriptor method : service.getMethods()) {
                if (gpbMsgDescMap.containsKey(method.getName())) {
                    LOG.error("[Gpb] the method [" + method.getName() + "] already registered.");
                }
                registerGpbMessage(method.getInputType());
                methodInputTypeMap.put(method.getName(), method.getInputType().getName());
            }
        }
        //  message
        for (Descriptor descriptor : fileDescriptor.getMessageTypes()) {
            registerGpbMessage(descriptor);
        }
    }

    public void registerGpbMessage(Descriptor descriptor) {
        gpbMsgDescMap.put(descriptor.getName(), new GpbMessageDesc(descriptor));
    }

    /**
     * pack the java type args to an gpb message.
     *
     * @return the gpb message used by async remote produce call.
     */
    public GpcCall pack(Method method, Object[] args) {
        String inputTypeName = methodInputTypeMap.get(method.getName());
        if (inputTypeName == null || inputTypeName.isEmpty()) {
            LOG.error("[Gpb] unregister method : " + method.getName());
            return null;
        }
        GpbMessageDesc messageDesc = gpbMsgDescMap.get(inputTypeName);
        if (messageDesc == null) {
            LOG.error("[Gpb] unregister message : " + inputTypeName);
            return null;
        }
        Message message = messageDesc.pack(args);
        return GpcCall.newBuilder()
                .setMethod(method.getName())
                .setParams(message.toByteString())
                .build();
    }

    /**
     * unpack the async remote produce call message to java type args.
     *
     * @return java type method's args.
     */
    public Object[] unpack(GpcCall call) {
        return unpack(call.getMethod(), call.getParams());
    }

    public Object[] unpack(String method, ByteString data) {
        if (method == null || method.isEmpty() || data == null || data.isEmpty()) return null;
        String inputTypeName = methodInputTypeMap.get(method);
        if (inputTypeName.isEmpty()) {
            LOG.error("[Gpb] unregister method : " + method);
            return null;
        }
        GpbMessageDesc messageDesc = gpbMsgDescMap.get(inputTypeName);
        if (messageDesc == null) {
            LOG.error("[Gpb] unregister message : " + inputTypeName);
            return null;
        }
        try {
            Message message = messageDesc.unpack(data);
            return message.getAllFields().values().toArray();
        } catch (InvalidProtocolBufferException e) {
            LOG.error("[Gpb] unpack message error. method name : " + method, e);
        }
        return null;
    }
}
