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

package org.ogcs.ax.component.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.ax.component.core.AxCodec;

import java.util.List;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/23
 * @since 1.0
 */
@Sharable
public class AxCodecHandler extends MessageToMessageCodec<ByteBuf, Object> {

    private static final Logger LOG = LogManager.getLogger(AxCodecHandler.class);
    private final AxCodec codec;

    public AxCodecHandler(AxCodec codec) {
        this.codec = codec;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
        try {
            ByteBuf encode = codec.encode(msg);
            if (encode != null)
                out.add(encode);
        } catch (Exception e) {
            LOG.warn("AxCodecHandler encode error : ", e);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        try {
            Object decode = codec.decode(msg);
            if (decode != null)
                out.add(decode);
        } catch (Exception e) {
            LOG.warn("AxCodecHandler decode error : ", e);
        }
    }
}
