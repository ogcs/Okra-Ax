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

package org.ogcs;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import org.ogcs.ax.gpb3.AxAnyProto.AxAny;
import org.ogcs.ax.gpb3.GpbD;
import org.ogcs.ax.gpb3.GpbD.Error;
import org.ogcs.ax.gpb3.GpbD.Response;
import org.ogcs.ax.utilities.AxAnyUtil;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public final class GpbReplys {

    public static Response error(int id, int state) {
        return Response.newBuilder()
                .setId(id)
                .setError(
                        Error.newBuilder()
                                .setState(state)
                                .build()
                )
                .build();
    }

    public static Response error(int id, int state, String message) {
        GpbD.Error.Builder build = Error.newBuilder();
        build.setState(state);
        if (message != null) {
            build.setMsg(message);
        }
        build.build();
        return Response.newBuilder()
                .setId(id)
                .setError(build)
                .build();
    }

    public static Response error(int id, Error error) {
        return Response.newBuilder()
                .setId(id)
                .setError(error)
                .build();
    }

    public static Response response(int id, AxAny any) {
        return Response.newBuilder()
                .setId(id)
                .setData(any)
                .build();
    }

    public static Response response(int id, Message message) {
        return response(id, any(AxAnyUtil.fetchMsgId(message), message.toByteString()));
    }

    public static Response response(int id, Message.Builder builder) {
        Message msg = builder.build();
        return response(id, any(AxAnyUtil.fetchMsgId(msg), msg.toByteString()));
    }

    public static Response response(int id, long msgId, Message message) {
        return response(id, any(msgId, message.toByteString()));
    }

    public static Response response(int id, long msgId, Message.Builder builder) {
        return response(id, any(msgId, builder.build().toByteString()));
    }

    public static AxAny any(long key, Message.Builder builder) {
        return any(key, builder.build().toByteString());
    }

    public static AxAny any(long key, Message msg) {
        return any(key, msg.toByteString());
    }

    public static AxAny any(long key, ByteString value) {
        return AxAny.newBuilder()
                .setKey(key)
                .setValue(value)
                .build();
    }
}
