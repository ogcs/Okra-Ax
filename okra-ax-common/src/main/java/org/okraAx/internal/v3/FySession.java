package org.okraAx.internal.v3;

import com.google.protobuf.Message;
import io.netty.channel.Channel;
import org.ogcs.app.AppContext;
import org.ogcs.app.NetSession;
import org.ogcs.app.ProxySingleCallback;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.component.GpbMethodComponent;
import org.okraAx.v3.GpcCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author TinyZ
 * @date 2017-02-17.
 */
public class FySession extends NetSession implements ProxySingleCallback<PlayerCallback> {

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
                    writeAndFlush(GpcCall.newBuilder()
                            .setMethod(method.getName())
                            .setParams(message.toByteString())
                            .build());
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
