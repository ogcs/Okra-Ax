package org.okraAx.internal.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.08.07
 */
public class NetSession implements AutoCloseable {

    private volatile Channel channel;

    public NetSession(Channel channel) {
        this.channel = channel;
    }

    public boolean isActive() {
        return channel != null && channel.isActive();
    }

    public Channel channel() {
        return channel;
    }

    public void writeAndFlush(Object msg) {
        writeAndFlush(msg, null);
    }

    public void writeAndFlush(Object message, ChannelFutureListener listener) {
        if (!isActive()) return;
        if (channel.isWritable()) {
            if (listener == null) {
                channel.writeAndFlush(message, channel.voidPromise());
            } else {
                channel.writeAndFlush(message).addListener(listener);
            }
        } else {
            channel.eventLoop().schedule(() -> {
                writeAndFlush(message, listener);
            }, 1L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void close() {
        if (channel != null) {
            channel.close();
        }
    }
}
