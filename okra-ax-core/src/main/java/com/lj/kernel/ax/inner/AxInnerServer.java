package com.lj.kernel.ax.inner;

import com.lj.kernel.ax.core.AxComponent;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.ogcs.netty.impl.TcpProtocolServer;

import static com.lj.kernel.ax.HandlerConst.*;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/4/23
 */
public class AxInnerServer extends TcpProtocolServer implements AxComponent {

    private static final AxInnerHandler AX_INNER_HANDLER = new AxInnerHandler();
    private String id;

    public AxInnerServer(String id, int port) {
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
                cp.addLast("axInboundDecoder", AX_INBOUND_DECODER);
                cp.addLast("pbEncoder", GPB_ENCODER);
                cp.addLast("axHandler", AX_INNER_HANDLER);
            }
        };
    }
}
