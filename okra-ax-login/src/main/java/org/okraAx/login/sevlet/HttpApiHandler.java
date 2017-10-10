package org.okraAx.login.sevlet;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpRequest;
import org.ogcs.app.Executor;
import org.ogcs.netty.handler.DisruptorAdapterHandler;
import org.okraAx.internal.v3.NetSession;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 * <p>
 * TODO: 使用@Sharable测试逻辑Handler是否可以共享
 */
@Sharable
public class HttpApiHandler extends DisruptorAdapterHandler<FullHttpRequest> {
    @Override
    protected Executor newExecutor(NetSession session, FullHttpRequest msg) {
        return new HttpRequestExecutor(session, msg);
    }
}
