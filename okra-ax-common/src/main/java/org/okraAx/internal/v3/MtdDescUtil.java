package org.okraAx.internal.v3;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gpb(Google Protocol Buffer) message descriptor manage util.
 * <p> 1. Must register message descriptor before server bootstrap. </p>
 *
 * @author TinyZ.
 * @version 2017.03.24
 * @since 2.0
 */
public enum MtdDescUtil {

    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(MtdDescUtil.class);

    private final Map<String, GpbMethodDesc> DESC_MAP = new ConcurrentHashMap<>();

    public GpcCall pack(Method method, Object[] args) {
        GpbMethodDesc methodDesc = getMethodDesc(method.getName());
        if (methodDesc == null) {
            LOG.error("[Gpb] unregister method : " + method.getName());
            return null;
        }
        Message message = methodDesc.pack(args);
        return GpcCall.newBuilder()
                .setMethod(method.getName())
                .setParams(message.toByteString())
                .build();
    }

    public Object[] unpack(GpcCall call) {
        return unpack(call.getMethod(), call.getParams());
    }

    public Object[] unpack(String mathod, ByteString data) {
        GpbMethodDesc methodDesc = getMethodDesc(mathod);
        if (methodDesc == null) return null;
        try {
            Message message = methodDesc.unpack(data);
            return message.getAllFields().values().toArray();
        } catch (InvalidProtocolBufferException e) {
            LOG.error("[Gpb] unpack message error. method name : " + mathod, e);
        }
        return null;
    }

    public GpbMethodDesc getMethodDesc(String methodName) {
        return DESC_MAP.get(methodName);
    }

    public void registerMethodDesc(Collection<ServiceDescriptor> collection) {
        for (ServiceDescriptor serviceDescriptor : collection) {
            for (MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
                registerMethodDesc(methodDescriptor);
            }
        }
    }

    public void registerMethodDesc(ServiceDescriptor serviceDescriptor) {
        for (MethodDescriptor methodDescriptor : serviceDescriptor.getMethods()) {
            registerMethodDesc(methodDescriptor);
        }
    }

    public void registerMethodDesc(MethodDescriptor methodDescriptor) {
        registerMethodDesc(new GpbMethodDesc(methodDescriptor));
    }

    public void registerMethodDesc(GpbMethodDesc desc) {
        if (DESC_MAP.containsKey(desc.getName())) {
            LOG.error("[Gpb] the method [" + desc.getName() + "] already registered.");
        }
        DESC_MAP.put(desc.getName(), desc);
    }
}