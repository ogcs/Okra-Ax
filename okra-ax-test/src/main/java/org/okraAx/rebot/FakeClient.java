package org.okraAx.rebot;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import org.okraAx.internal.handler.AxCodecHandler;
import org.okraAx.internal.handler.codec.AxGpbCodec;
import org.okraAx.internal.inner.AutoClient;
import org.okraAx.internal.inner.IrClient;
import org.okraAx.internal.v3.GpbProxyHandler;
import org.okraAx.v3.GpcCall;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author TinyZ.
 * @version 2017.05.10
 */
public class FakeClient {

    private Channel channel;
    private Queue<Runnable> actions = new ConcurrentLinkedQueue<>();

    private Map<String, Integer> futures = new ConcurrentHashMap<>();

    public void connect(String host, int port) {
//        IrClient client = new IrClient(host, port);
        AutoClient client = new AutoClient(host, port) {
            @Override
            protected ChannelHandler newChannelInitializer() {
                return new ChannelInitializer<Channel>() {
                    private final ChannelHandler FRAME_PREPENDER = new LengthFieldPrepender(4, false);
                    private final AxCodecHandler CODEC_HANDLER = new AxCodecHandler(new AxGpbCodec(GpcCall.getDefaultInstance()));

                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline cp = ch.pipeline();
                        cp.addLast("frame", new LengthFieldBasedFrameDecoder(102400, 0, 4, 0, 4)); // 102400 = 100k
                        cp.addLast("prepender", FRAME_PREPENDER);
                        cp.addLast("codec", CODEC_HANDLER);
                        cp.addLast("handler", new SimpleChannelInboundHandler<GpcCall>() {
                            @Override
                            protected void channelRead0(ChannelHandlerContext ctx, GpcCall msg) throws Exception {

                                System.out.println("..." + msg.getMethod());
                                if (futures.containsKey(msg.getMethod())) {
                                    futures.remove(msg.getMethod());
                                    this.notifyAll();
                                }


                            }
                        });
                    }
                };
            }
        };
        client.start();
        this.channel = client.client();
    }

    public void addLastAction(Runnable task) {
        actions.offer(task);
    }

    public void doAction() {
        try {
            Runnable action = actions.poll();
            if (action != null) {
                action.run();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //
        }
    }

    public void writeAndFlush(Object msg) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(msg);
        }
    }

    public boolean waitForCallback(String method) {
        futures.put(method, 0);
        synchronized (this) {
            try {
                this.wait(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return futures.isEmpty();
    }



}
