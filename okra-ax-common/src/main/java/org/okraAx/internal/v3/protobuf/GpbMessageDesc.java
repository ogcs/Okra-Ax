package org.okraAx.internal.v3.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.MethodDescriptor;
import com.google.protobuf.DynamicMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

    public Message pack(Object[] args) {
        DynamicMessage.Builder builder = DynamicMessage.newBuilder(descriptor);
        List<FieldDescriptor> fields = descriptor.getFields();
        if (!fields.isEmpty() && fields.size() == args.length) {
            for (int i = 0; i < fields.size(); i++) {
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
}
