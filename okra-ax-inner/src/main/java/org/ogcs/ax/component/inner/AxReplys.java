/*
 *   Copyright 2016 - 2026 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package org.ogcs.ax.component.inner;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import org.ogcs.ax.gpb.OkraAx.AxError;
import org.ogcs.ax.gpb.OkraAx.AxInbound;
import org.ogcs.ax.gpb.OkraAx.AxOutbound;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @since 1.0
 */
public final class AxReplys {

    public static AxInbound axInbound(long source, int id, int method, MessageLite data) {
        return axInbound(source, id, method, data.toByteString());
    }

    public static AxInbound axInbound(long source, int id, int method, ByteString data) {
        return AxInbound.newBuilder()
                .setRid(id)
                .setCmd(method)
                .setSource(source)
                .setData(data)
                .build();
    }

    public static AxOutbound error(int rid, AxError error) {
        return AxOutbound.newBuilder()
                .setRid(rid)
                .setError(error)
                .build();
    }

    public static AxOutbound error(int rid, int state) {
        return AxOutbound.newBuilder()
                .setRid(rid)
                .setError(
                        AxError.newBuilder()
                                .setState(state)
                                .build()
                )
                .build();
    }

    public static AxOutbound error(int rid, int state, String msg) {
        return AxOutbound.newBuilder()
                .setRid(rid)
                .setError(
                        AxError.newBuilder()
                                .setState(state)
                                .setMsg(msg)
                                .build()
                )
                .build();
    }

    /**
     * @param message {@link MessageLite}
     * @param ids     gate node's id
     * @return The outbound message
     */
    public static AxOutbound axOutbound(int rid, MessageLite message, Long... ids) {
        return axOutbound(rid, message.toByteString(), ids);
    }

    /**
     * @param data message extend {@link MessageLite#toByteString()}
     * @param ids  gate node's id
     * @return The outbound message
     */
    public static AxOutbound axOutbound(int rid, ByteString data, Long... ids) {
        AxOutbound.Builder builder = AxOutbound.newBuilder();
        for (Long id : ids) {
            builder.addTarget(id);
        }
        builder.setRid(rid);
        builder.setData(data);
        return builder.build();
    }
}
