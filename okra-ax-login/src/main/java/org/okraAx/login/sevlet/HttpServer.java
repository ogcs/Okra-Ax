//package org.okraAx.login.sevlet;
//
//import io.netty.channel.ChannelHandler;
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.MessageToMessageDecoder;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpRequestDecoder;
//import io.netty.handler.codec.http.HttpResponseEncoder;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import io.netty.handler.timeout.IdleStateHandler;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.ogcs.netty.impl.TcpProtocolServer;
//
//import java.util.List;
//
///**
// * @author : TinyZ.
// * @email : ogcs_tinyz@outlook.com
// * @date : 2015/12/23
// */
//public class HttpServer extends TcpProtocolServer {
//
//    private static final Logger LOG = LogManager.getLogger(HttpServer.class);
//
//    public HttpServer(int port) {
//        setPort(port);
//    }
//
//    private static final ChannelHandler HTTP_API_HANDLER = new HttpApiHandler();
//
//    @Override
//    protected ChannelHandler newChannelInitializer() {
//        return new ChannelInitializer<NioSocketChannel>() {
//            @Override
//            protected void initChannel(NioSocketChannel ch) throws Exception {
//                // HTTP管理
//                ChannelPipeline cp = ch.pipeline();
//                cp.addLast("decoder", new HttpRequestDecoder());
//                cp.addLast("encoder", new HttpResponseEncoder());
//                cp.addLast("aggregator", new HttpObjectAggregator(1048576));
//
//                cp.addLast("idleState", new IdleStateHandler(60, 60, 60));
//                cp.addLast("idleHandler", new MessageToMessageDecoder<IdleStateEvent>() {
//                    @Override
//                    protected void decode(ChannelHandlerContext ctx, IdleStateEvent msg, List<Object> out) throws Exception {
//                        if (msg.state().equals(IdleState.ALL_IDLE)) {
//                            LOG.warn("Channel {} has been idle, it will be disconnected now.", ctx.channel());
//                            ctx.close();
//                        }
//                    }
//                });
//                cp.addLast("handler", HTTP_API_HANDLER);
//            }
//        };
//    }
//
//    @Override
//    public void stop() {
//        super.stop();
//    }
//}
