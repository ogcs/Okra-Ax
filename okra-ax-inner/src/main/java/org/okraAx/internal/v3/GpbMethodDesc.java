package org.okraAx.internal.v3;

import com.google.protobuf.*;
import com.google.protobuf.Descriptors.MethodDescriptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * @author TinyZ
 * @date 2017-02-28.
 */
public final class GpbMethodDesc {

    private static final Logger LOG = LogManager.getLogger(GpbMethodDesc.class);
    /**
     * Method Descriptor.
     * <pre>
     *     Google Protocol Buffer Service.
     * </pre>
     */
    private final MethodDescriptor descriptor;

    public GpbMethodDesc(MethodDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public Message unpack(ByteString byteString) throws InvalidProtocolBufferException {
        return DynamicMessage
                .newBuilder(descriptor.getInputType())
                .mergeFrom(byteString)
                .build();
    }

    public Message unpack(byte[] bytes) throws InvalidProtocolBufferException {
        return DynamicMessage
                .newBuilder(descriptor.getInputType())
                .mergeFrom(bytes)
                .build();
    }

    public Message pack(Object[] args) {
        Descriptors.Descriptor inputType = descriptor.getInputType();
        DynamicMessage.Builder builder = DynamicMessage.newBuilder(inputType);
        List<Descriptors.FieldDescriptor> fields = inputType.getFields();
        if (fields.size() != args.length) {
            return null;
        }
        for (int i = 0; i < fields.size(); i++) {
            try {
                builder.setField(fields.get(i), args[i]);
            } catch (Exception e) {
                LOG.error("[Gpb] - Java Type : " + fields.get(i).getJavaType().toString()
                        + ", Real Type : " + args[i].getClass().toString());
            }
        }
        return builder.build();
    }
}
