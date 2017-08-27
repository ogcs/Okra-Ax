package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ServiceProxy;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.net.NetSession;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.Module;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Login Player.
 *
 * @author TinyZ.
 * @version 2017.08.26
 * @since 2.0
 */
public final class User implements Modules, ServiceProxy<PlayerCallback> {

    private static final Logger LOG = LogManager.getLogger(User.class);

    private static final PlayerCallback EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    }, PlayerCallback.class);

    private volatile NetSession session;
    private volatile PlayerCallback callback;

    /**
     * 账户信息
     */
    private AccountBean accountBean;
    /**
     * 角色信息
     */
    private RoleBean roleBean;
    /**
     * 挂载的模块
     */
    private Map<Class<? extends Module>, Module> modules = new ConcurrentHashMap<>();

    public User() {
    }

    public User(NetSession session) {
        setSession(session);
    }

    public long id() {
        return accountBean != null ? accountBean.getUid() : -1L;
    }

    public void setSession(NetSession session) {
        this.session = session;
        this.callback = newProxyInstance(new GpbInvocationHandler(session), PlayerCallback.class);
    }

    /**
     *
     */
    public PlayerCallback userClient() {
        if (session == null || !session.isActive())
            return EMPTY;
        return proxy();
    }

    /**
     * 玩家登录成功后加载私人数据
     */
    @Override
    public void lazyLoad() {
        //  initialize modules.
        for (Module module : modules.values()) {
            module.init();
        }
    }

    public void disconnect() {
        for (Module module : modules.values()) {
            module.flushToDB();
        }
    }

    @Override
    public void registerModule(Module module) {
        if (module == null) {
            LOG.error("the registered module is null.");
            return;
        }
        if (modules.containsKey(module.getClass())) {
            LOG.error("the registered module is duplicate. module:" + module.getClass().getName());
        }
        modules.put(module.getClass(), module);
    }

    /**
     * 获取角色的模块
     * <pre>注意使用: 避免强制类型转换错误<pre/>
     */
    @Override
    public <T extends Module> T module(Class<T> clz) {
        if (!modules.containsKey(clz)) {
            LOG.error("Unknown Module : " + clz.getName());
            return null;
        }
        Module module = modules.get(clz);
        return clz.isAssignableFrom(module.getClass()) ? (T) module : null;
    }

    protected static <P> P newProxyInstance(InvocationHandler handler, Class<P> clzOfProxy) {
        return (P) Proxy.newProxyInstance(
                User.class.getClassLoader(),
                new Class[]{clzOfProxy},
                handler);
    }

    public AccountBean getAccountBean() {
        return accountBean;
    }

    public void setAccountBean(AccountBean accountBean) {
        this.accountBean = accountBean;
    }

    public RoleBean getRoleBean() {
        return roleBean;
    }

    public void setRoleBean(RoleBean roleBean) {
        this.roleBean = roleBean;
    }

    public NetSession session() {
        return session;
    }

    @Override
    public PlayerCallback proxy() {
        return callback;
    }
}
