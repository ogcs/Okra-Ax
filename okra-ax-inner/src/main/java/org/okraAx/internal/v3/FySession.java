package org.okraAx.internal.v3;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.ProxySession;
import org.okraAx.internal.v3.callback.PlayerCallback;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ
 * @date 2017-02-17.
 */
public class FySession extends NetSession implements ProxySession<PlayerCallback> {

    private GpbMethodComponent methodComponent = AppContext.getBean(GpbMethodComponent.class);

    private final PlayerCallback callback;

    public FySession(Channel channel) {
        super(channel);
        //  callback
        this.callback = (PlayerCallback) Proxy.newProxyInstance(
                this.getClass().getClassLoader(),
                new Class[]{PlayerCallback.class}, new IrInvocationHandler()
        );
    }

    @Override
    public PlayerCallback callback() {
        return callback;
    }

    private class IrInvocationHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            GpbMethodDesc methodDesc = methodComponent.getMethodDescriptor(method.getName());
            if (methodDesc != null) {
                Message message = methodDesc.pack(args);
                if (message != null) {
                    writeAndFlush(message);
                } else {
                    //  转换错误
                }
            } else {
                //  方法未注册
            }
            return null;
        }
    }
}
