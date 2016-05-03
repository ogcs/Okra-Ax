package com.lj.kernel.gate;

import com.lj.kernel.gpb.GpbD.Request;
import com.lj.kernel.gpb.GpbD.Response;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import org.ogcs.ax.component.AxCoInfo;
import org.ogcs.ax.component.AxComponent;
import org.ogcs.netty.impl.TcpProtocolServer;

import static org.ogcs.ax.component.HandlerConst.FRAME_PREPENDER;
import static org.ogcs.ax.component.HandlerConst.GPB_ENCODER;


/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxGate extends TcpProtocolServer implements AxComponent {

    private static final ProtobufDecoder GPB_REQUEST_DECODER = new ProtobufDecoder(Request.getDefaultInstance());
    private static final AxGateHandler AX_REQUEST_HANDLER = new AxGateHandler();
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
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
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
