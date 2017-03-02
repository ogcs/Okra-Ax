package org.okraAx.login;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.handler.codec.http.FullHttpRequest;
import org.ogcs.app.Executor;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.DisruptorAdapterHandler;

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
    protected Executor newExecutor(Session session, FullHttpRequest msg) {
        return new HttpRequestExecutor(session, msg);
    }
}
