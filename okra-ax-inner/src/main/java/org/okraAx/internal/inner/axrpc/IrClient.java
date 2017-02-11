package org.okraAx.internal.inner.axrpc;

import io.netty.channel.ChannelHandler;
import org.okraAx.internal.inner.AutoClient;

/**
 * @author TinyZ
 * @date 2017-02-09.
 */
public class IrClient extends AutoClient {

    public IrClient(String host, int port) {
        super(host, port);
    }

    @Override
    protected ChannelHandler newChannelInitializer() {
        return new IrChannelInitializer();
    }


}
