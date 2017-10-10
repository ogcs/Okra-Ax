package org.okraAx.login.sevlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.okraAx.internal.v3.NetSession;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author : TinyZ.
 * @email : tinyzzh815@gmail.com
 * @date : 2016/5/5
 * @since 1.0
 */
public final class HttpUtil {

    private HttpUtil() {
        // no-op
    }

    public static void response(NetSession session, String msg) {
        HttpResponse response;
        if (msg != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        } else {
            response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.OK);
        }
        session.writeAndFlush(response, ChannelFutureListener.CLOSE);
    }

    public static void status(NetSession session, HttpResponseStatus status) {
        session.writeAndFlush(new DefaultFullHttpResponse(HTTP_1_1, status), ChannelFutureListener.CLOSE);
    }
}
