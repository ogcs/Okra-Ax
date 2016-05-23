/*
 *         Copyright 2016 - 2026 TinyZ.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ogcs.ax.component.handler.codec;

import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import org.ogcs.ax.component.handler.AxCodec;

import static io.netty.buffer.Unpooled.wrappedBuffer;

/**
 * required:
 * Google Protocol Buffer 2.5.0 above (exclude 3.x)
 * <p/>
 * see {@link io.netty.handler.codec.protobuf.ProtobufDecoder} and {@link io.netty.handler.codec.protobuf.ProtobufEncoder}
 *
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/23
 * @since 1.0
 */
public class AxGpbCodec implements AxCodec {

    private final MessageLite prototype;
    private final ExtensionRegistryLite registry;

    public AxGpbCodec(MessageLite msg) {
        this(msg, null);
    }

    public AxGpbCodec(MessageLite msg, ExtensionRegistryLite registry) {
        this.prototype = msg;
        this.registry = registry;
    }

    @Override
    public MessageLite decode(ByteBuf msg) throws Exception {
        // fixed error: msg.array() method cause java.lang.UnsupportedOperationException: direct buffer
        // see: http://stackoverflow.com/questions/25222392/netty-correct-usage-of-a-decoder
        final byte[] array;
        final int offset;
        final int length = msg.readableBytes();
        if (msg.hasArray()) {
            array = msg.array();
            offset = msg.arrayOffset() + msg.readerIndex();
        } else {
            array = new byte[length];
            msg.getBytes(msg.readerIndex(), array, 0, length);
            offset = 0;
        }
        if (this.registry == null) {
            return prototype.getParserForType().parseFrom(array, offset, length);
        } else {
            return prototype.getParserForType().parseFrom(array, offset, length, registry);
        }
    }

    @Override
    public ByteBuf encode(Object obj) {
        if (obj instanceof MessageLite) {
            return wrappedBuffer(((MessageLite) obj).toByteArray());
        } else if (obj instanceof MessageLite.Builder) {
            return wrappedBuffer(((MessageLite.Builder) obj).build().toByteArray());
        } else if (obj instanceof ByteBuf) {
            return (ByteBuf) obj;
        }
        return null;
    }
}
