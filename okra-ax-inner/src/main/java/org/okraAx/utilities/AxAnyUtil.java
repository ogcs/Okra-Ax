package org.okraAx.utilities;

import com.google.protobuf.DescriptorProtos.MessageOptions;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.ogcs.utilities.math.murmur.Murmur2;
import org.okraAx.v3.AxAnyProto;
import org.okraAx.v3.AxOptions;

/**
 * @author TinyZ
 * @date 2016-10-21.
 */
public final class AxAnyUtil {

    public static <T extends Message> AxAnyProto.AxAny pack(T message) {
        return AxAnyProto.AxAny
                .newBuilder()
                .setKey(fetchMsgId(message))
                .setValue(message.toByteString())
                .build();
    }

    public static <T extends Message> T unpack(AxAnyProto.AxAny any, Class<T> message) throws InvalidProtocolBufferException {
        T var1 = Internal.getDefaultInstance(message);
        if (!is(any, var1)) {
            throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");
        }
        T result = (T) var1.getParserForType()
                .parseFrom(any.getValue());
        return result;
    }

    public static long fetchMsgId(Message message) {
        Descriptor descriptorForType = message.getDescriptorForType();
        MessageOptions options = descriptorForType.getOptions();
        return options.hasExtension(AxOptions.messageId) ?
                options.getExtension(AxOptions.messageId) :
                Murmur2.hash(descriptorForType.getFullName());
    }

    public static <T extends Message> boolean is(AxAnyProto.AxAny any, Class<T> clazz) {
        return is(any, Internal.getDefaultInstance(clazz));
    }

    public static <T extends Message> boolean is(AxAnyProto.AxAny any, T message) {
        return any.getKey() == fetchMsgId(message);
    }
}
