package com.lj.kernel.ax.inner;

import com.google.protobuf.ByteString;
import com.google.protobuf.MessageLite;
import com.lj.kernel.gpb.OkraAx.AxError;
import com.lj.kernel.gpb.OkraAx.AxInbound;
import com.lj.kernel.gpb.OkraAx.AxOutbound;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/25
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
        builder.setData(data);
        return builder.build();
    }
}
