package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.v3.NetSession;
import org.okraAx.internal.v3.ProxyClient;
import org.okraAx.internal.v3.protobuf.GpbInvocationHandler;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.Module;
import org.okraAx.utilities.ProxyUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Login Player.
 *
 * @author TinyZ.
 * @version 2017.08.26
 */
public final class User implements Modules {

    private static final Logger LOG = LogManager.getLogger(User.class);

    private static final PlayerCallback DEFAULT_CALLBACK =
            ProxyUtil.newProxyInstance(PlayerCallback.class, (proxy, method, args) -> {
                //  no-op
                LOG.info("Empty proxy instance invoked by [{}]", method.getName());
                return null;
            });

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

    private volatile ProxyClient<PlayerCallback> userClient;

    public User() {
    }

    /**
     * 初始化User
     */
    public void init(NetSession session) {
        userClient = new ProxyClient<>(session, new GpbInvocationHandler(session), DEFAULT_CALLBACK);
        userClient.initialize();
    }

    public long id() {
        return accountBean != null ? accountBean.getUid() : -1L;
    }

    public ProxyClient<PlayerCallback> proxyClient() {
        return userClient;
    }

    public PlayerCallback callback() {
        return userClient.impl();
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
        return clz.isAssignableFrom(module.getClass()) ? clz.cast(module) : null;
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
}
