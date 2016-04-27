package com.lj.kernel.remote;

import com.lj.kernel.ax.core.AxComponent;
import com.lj.kernel.remote.command.AxRemoteHandler;
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
public class AxRemote extends TcpProtocolServer implements AxComponent {

    private static final AxRemoteHandler AX_REMOTE_HANDLER = new AxRemoteHandler();
    private String id;

    public AxRemote(String id, int port) {
        this.id = id;
        this.port = port;
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("frame", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                cp.addLast("prepender", FRAME_PREPENDER);
                cp.addLast("gpbDecoder", GPB_INBOUND_DECODER);
                cp.addLast("gpbEncoder", GPB_ENCODER);
                cp.addLast("axRemote", AX_REMOTE_HANDLER);
            }
        };
    }
}
