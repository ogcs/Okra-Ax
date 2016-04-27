package com.lj.kernel.ax;

import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.lj.kernel.gpb.generated.GpbD;
import com.lj.kernel.gpb.generated.GpbD.Inbound;
import com.lj.kernel.gpb.generated.GpbD.Outbound;
import com.lj.kernel.gpb.generated.GpbD.Response;
import com.lj.kernel.gpb.generated.GpbD.Error;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/25
 */
public final class AxReplys {


    public static Inbound inbound(long uid, int id, int method, MessageLite data) {
        return inbound(uid, id, method, data.toByteString());
    }

    public static Inbound inbound(long uid, int id, int method, ByteString data) {
        return Inbound.newBuilder()
                .setId(id)
                .setMethod(method)
                .setUid(uid)
                .setData(data)
                .build();
    }

    /**
     *
     * @param message {@link GpbD.Response}
     * @param ids gate node's id
     * @return The outbound message
     */
    public static Outbound outbound(MessageLite message, Long... ids) {
        return outbound(message.toByteString(), ids);
    }

    /**
     *
     * @param data {@link GpbD.Response} message's  {@link Response#toByteString()}
     * @param ids gate node's id
     * @return The outbound message
     */
    public static Outbound outbound(ByteString data, Long... ids) {
        Outbound.Builder builder = Outbound.newBuilder();
        for (Long id : ids) {
            builder.addUids(id);
        }
        builder.setData(data);
        return builder.build();
    }

    /**
     *
     * @param data {@link GpbD.Response} message's  {@link Response#toByteString()}
     * @param ids gate node's id
     * @return The outbound message
     */
    public static Outbound outbound(ByteString data, Iterable<Long> ids) {
        return Outbound.newBuilder()
                .addAllUids(ids)
                .setData(data)
                .build();
    }

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
        Error.Builder build = Error.newBuilder();
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

    public static Response response(int id, ByteString data) {
        return Response.newBuilder()
                .setId(id)
                .setData(data)
                .build();
    }

    public static Response response(int id, Message message) {
        return response(id, message.toByteString());
    }

    public static Response response(int id, Message.Builder builder) {
        return response(id, builder.build().toByteString());
    }
}
