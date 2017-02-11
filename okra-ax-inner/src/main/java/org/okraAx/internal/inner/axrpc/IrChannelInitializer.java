package org.okraAx.internal.inner.axrpc;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.AxRpcHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.v3.Rpc;

/**
 * @author TinyZ
 */
public final class IrChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    private static final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);
    private static final AxCodecHandler CODEC_HANDLER = new AxCodecHandler(new AxGpbCodec(Rpc.getDefaultInstance()));
    private static final AxRpcHandler HANDLER = new AxRpcHandler();

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline cp = ch.pipeline();
        cp.addLast("frame", new LengthFieldBasedFrameDecoder(102400, 0, 4, 0, 4)); // 102400 = 100k
        cp.addLast("prepender", FRAME_PREPENDER);
        cp.addLast("codec", CODEC_HANDLER);
        cp.addLast("handler", HANDLER);
    }
}
