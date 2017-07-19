package org.okraAx.internal.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.07.08
 */
public abstract class AbstractSession implements AutoCloseable {

    protected Channel channel;

    public AbstractSession(Channel channel) {
        this.channel = channel;
    }

    public boolean isOnline() {
        return channel != null && channel.isActive();
    }

    public void writeAndFlush(Object msg) {
        writeAndFlush(msg, null);
    }

    public void writeAndFlush(Object message, ChannelFutureListener listener) {
        if (!isOnline()) return;
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
    public void close() throws Exception {
        if (channel != null){
            channel.close();
        }
    }
}
