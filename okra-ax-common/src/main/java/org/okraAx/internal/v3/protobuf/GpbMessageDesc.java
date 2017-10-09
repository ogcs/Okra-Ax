package org.okraAx.internal.v3.protobuf;

import com.google.protobuf.*;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author TinyZ
 * @version 2017.05.14.
 */
public final class GpbMessageDesc {

    private static final Logger LOG = LogManager.getLogger(GpbMessageDesc.class);
    /**
     * Google Protocol Buffer Message Descriptor.
     */
    private final Descriptor descriptor;

    public GpbMessageDesc(Descriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Deprecated
    public GpbMessageDesc(MethodDescriptor descriptor) {
        this.descriptor = descriptor.getInputType();
    }

    public String getName() {
        return descriptor.getName();
    }

    public Message unpack(ByteString byteString) throws InvalidProtocolBufferException {
        return DynamicMessage
                .newBuilder(descriptor)
                .mergeFrom(byteString)
                .build();
    }

    public Message unpack(byte[] bytes) throws InvalidProtocolBufferException {
        return DynamicMessage
                .newBuilder(descriptor)
                .mergeFrom(bytes)
                .build();
    }

    /**
     * 根据JavaMethod, 反序列化{@link Message}, 返回调用函数所需的参数.
     *
     * @param method     对应的Java方法
     * @param byteString 待反序列化的数据
     */
    public Object[] unpackWithJavaMethod(java.lang.reflect.Method method, ByteString byteString) throws InvalidProtocolBufferException {
        Message message = unpack(byteString);
        Object[] args = new Object[method.getParameterCount()];
        Class<?>[] parameterTypes = method.getParameterTypes();
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            args[entry.getKey().getIndex()] = entry.getValue();
        }
        for (int i = 0; i < args.length; i++) {
            if (parameterTypes[i] == Object.class)
                continue;
            if (args[i] == null) {  //  set default value
                if (parameterTypes[i] == int.class) {
                    args[i] = 0;
                } else if (parameterTypes[i] == double.class) {
                    args[i] = 0.0D;
                } else if (parameterTypes[i] == boolean.class) {
                    args[i] = false;
                } else if (parameterTypes[i] == byte.class) {
                    args[i] = 0x00;
                } else if (parameterTypes[i] == short.class) {
                    args[i] = 0;
                } else if (parameterTypes[i] == long.class) {
                    args[i] = 0L;
                } else if (parameterTypes[i] == float.class) {
                    args[i] = 0.0F;
                }
            } else {
                if (args[i].getClass() != parameterTypes[i]) {  //  类型不同 - 类型转换
                    try {
                        args[i] = parameterTypes[i].cast(args[i]);
                    } catch (ClassCastException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return args;
    }

    /**
     * <pre>
     *     1. 原始类型数据int float double boolean等和null不同
     * </pre>
     */
    public Object[] unpackWithParamCount(int parameterCount, ByteString byteString) throws InvalidProtocolBufferException {
        Message message = unpack(byteString);
        Object[] args = new Object[parameterCount];
        for (Map.Entry<Descriptors.FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            args[entry.getKey().getIndex()] = entry.getValue();
        }
        return args;
    }

    public Message pack(Object[] args) {
        DynamicMessage.Builder builder = DynamicMessage.newBuilder(descriptor);
        List<FieldDescriptor> fields = descriptor.getFields();
        if (!fields.isEmpty() && fields.size() == args.length) {
            for (int i = 0; i < fields.size(); i++) {
                if (args[i] == null)
                    continue;
                try {
                    builder.setField(fields.get(i), args[i]);
                } catch (Exception e) {
                    LOG.error("[Gpb] - Java Type : " + fields.get(i).getJavaType().toString()
                            + ", Real Type : " + args[i].getClass().toString());
                }
            }
        }
        return builder.build();
    }

    public Descriptor getDescriptor() {
        return descriptor;
    }
}
