package org.ogcs.ax.gate;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.inner.AxComponent;
import org.ogcs.netty.impl.TcpProtocolServer;


/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxGate extends TcpProtocolServer implements AxComponent {

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);
    private static final ChannelHandler GPB_ENCODER = new ProtobufEncoder();
    private static final ChannelHandler GPB_REQUEST_DECODER = new ProtobufDecoder(Request.getDefaultInstance());
    private static final ChannelHandler AX_REQUEST_HANDLER = new AxGateHandler();

    private AxCoInfo axCoInfo;
    private String id;

    public AxGate(String id, int port) {
        this.id = id;
        this.port = port;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(102400, 0, 4, 0, 4)); // 102400 = 100k
                cp.addLast("prepender", FRAME_PREPENDER);
                cp.addLast("decoder", GPB_REQUEST_DECODER);
                cp.addLast("encoder", GPB_ENCODER);
                cp.addLast("axRequest", AX_REQUEST_HANDLER);
            }
        };
    }
}
