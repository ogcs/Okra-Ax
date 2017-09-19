package org.okraAx.internal.v3;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.bean.ConnectionInfo;
import org.okraAx.internal.net.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.utilities.ProxyUtil;
import org.okraAx.utilities.ReflectionUtil;

/**
 *
 */
public class ProxyClient<T> {

    private static final Logger LOG = LogManager.getLogger(ProxyClient.class);
    /**
     *
     */
    private final NetSession session;
    /**
     * 节点的连接信息
     */
    private final ConnectionInfo info;
    /**
     * 缺省时的实例
     */
    private final T defaultImpl;
    /**
     * 服务
     */
    private volatile T services;

    public ProxyClient(NetSession session, ConnectionInfo info, T bean) {
        this.session = session;
        this.info = info;
        this.defaultImpl = bean;
    }

    public T impl() {
        return services == null ? defaultImpl : services;
    }

    public void initialize() {
        try {
            Class<T> clz = ReflectionUtil.tryGetGenericInterface(this.defaultImpl);
            this.services = ProxyUtil.newProxyInstance(clz, new GpbInvocationHandler(session));
        } catch (Exception e) {
            LOG.error("ProxyClient initialize failed.", e);
        }
    }

    public ConnectionInfo getInfo() {
        return info;
    }

    public NetSession getSession() {
        return session;
    }

}
