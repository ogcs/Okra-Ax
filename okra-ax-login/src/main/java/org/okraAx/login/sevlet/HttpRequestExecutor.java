package org.okraAx.login.sevlet;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2015/11/20
 */
public class HttpRequestExecutor implements Executor {

    private static final Logger LOG = LogManager.getLogger(HttpRequestExecutor.class);

    protected Session session;
    protected FullHttpRequest request;

    public HttpRequestExecutor(Session session, FullHttpRequest request) {
        this.session = session;
        this.request = request;
    }

//    public static final GUEST_LOGIN cmd = new GUEST_LOGIN();

    @Override
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        try {
            String uri = request.getUri();
            if (uri.isEmpty()) {
                simple(session.channel(), HttpResponseStatus.FORBIDDEN);
                return;
            }
            String[] split = request.getUri().split("\\?");
            if (split.length < 2) {
                simple(session.channel(), HttpResponseStatus.FORBIDDEN);
                return;
            }
            Map<String, String> map = url2Map(split[1]);
//            cmd.execute(session, map);

//            String rp = HttpApi.handleRequest(session, request);
//            if (rp == null) {
//                simple(session.ctx().channel(), HttpResponseStatus.FORBIDDEN);
//            } else {
//                response(session.ctx(), rp);
//            }
        } catch (Exception e) {
            session.close();
            LOG.info("HTTP Api throw exception : ", e);
        }
    }

    private static void simple(Channel channel, HttpResponseStatus status) {
        ChannelFuture channelFuture = channel.writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status));
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    private static void response(ChannelHandlerContext ctx, String msg) {
        HttpResponse response;
        if (msg != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(msg.getBytes());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);
        }
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(response);
        channelFuture.addListener(ChannelFutureListener.CLOSE);
    }

    public static Map<String, String> url2Map(String param) {
        Map<String, String> map = new HashMap<String, String>();
        String[] array = param.split("&");
        for (String s : array) {
            int index = s.indexOf("=");
            if (index == -1 || s.length() < index + 1) {
                continue;
            }
            map.put(s.substring(0, index), s.substring(index + 1, s.length()));
        }
        return map;
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
