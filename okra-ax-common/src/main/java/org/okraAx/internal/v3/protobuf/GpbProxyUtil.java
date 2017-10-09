package org.okraAx.internal.v3.protobuf;

import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;

/**
 * @author TinyZ.
 * @version 2017.10.03
 */
public final class GpbProxyUtil {

    private GpbProxyUtil() {
        //  no-op
    }

    public static <T> ProxyClient<T> newProxyClient(NetSession session, T def) {
        ProxyClient<T> client = new ProxyClient<>(session, new GpbInvocationHandler(session), def);
        client.initialize();
        return client;
    }

    public static <T> ProxyClient<T> newRelayProxyClient(NetSession session, T def) {
        ProxyClient<T> client = new ProxyClient<>(session, new GpbRelayInvocationHandler(session), def);
        client.initialize();
        return client;
    }

}
