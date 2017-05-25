package org.okraAx.internal.v3;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.Descriptors.ServiceDescriptor;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.v3.protobuf.GpbMessageDesc;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Gpb(Google Protocol Buffer) message descriptor manage util.
 * <p> 1. Must register message descriptor before server bootstrap. </p>
 * <zh-cn>
 * GPB方法文件描述管理工具. 管理GPB文件产生的方法描述.
 * 当异步远程过程调用(ARPC)时，解析消息体(Call)
 * </zh-cn>
 *
 * @author TinyZ.
 * @version 2017.03.24
 * @since 2.0
 */
public enum MtdDescUtil {

    INSTANCE;

    private static final Logger LOG = LogManager.getLogger(MtdDescUtil.class);

    /**
     * GPB方法描述符映射键值对
     */
    private final Map<String /* method's name*/, GpbMessageDesc> DESC_MAP = new ConcurrentHashMap<>();

    /**
     * pack the java type args to an gpb message.
     *
     * @return the gpb message used by async remote produce call.
     */
    public GpcCall pack(Method method, Object[] args) {
        GpbMessageDesc methodDesc = getMethodDesc(method.getName());
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

    /**
     * unpack the async remote produce call message to java type args.
     *
     * @return java type method's args.
     */
    public Object[] unpack(GpcCall call) {
        return unpack(call.getMethod(), call.getParams());
    }

    public Object[] unpack(String mathod, ByteString data) {
        GpbMessageDesc methodDesc = getMethodDesc(mathod);
        if (methodDesc == null) return null;
        try {
            Message message = methodDesc.unpack(data);
            return message.getAllFields().values().toArray();
        } catch (InvalidProtocolBufferException e) {
            LOG.error("[Gpb] unpack message error. method name : " + mathod, e);
        }
        return null;
    }

    public GpbMessageDesc getMethodDesc(String methodName) {
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
        registerMethodDesc(new GpbMessageDesc(methodDescriptor));
    }

    public void registerMethodDesc(GpbMessageDesc desc) {
        if (DESC_MAP.containsKey(desc.getName())) {
            LOG.error("[Gpb] the method [" + desc.getName() + "] already registered.");
        }
        DESC_MAP.put(desc.getName(), desc);
    }
}