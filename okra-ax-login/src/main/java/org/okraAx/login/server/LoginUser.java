package org.okraAx.login.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.okraAx.common.PlayerCallback;
import org.okraAx.internal.v3.ProxyPlayer;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.Modules;
import org.okraAx.login.role.module.Module;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Login Player.
 *
 * @author TinyZ.
 * @version 2017.02.12
 * @since 2.0
 */
public final class LoginUser extends ProxyPlayer<PlayerCallback> implements Modules {

    private static final Logger LOG = LogManager.getLogger(LoginUser.class);

    private static final PlayerCallback EMPTY = newProxyInstance((proxy, method, args) -> {
        //  no-op
        LOG.info("Empty proxy instance invoked by [{}]", method.getName());
        return null;
    }, PlayerCallback.class);

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

    public LoginUser() {
        super(null, PlayerCallback.class);
    }

    public LoginUser(Session session) {
        super(session, PlayerCallback.class);
    }

    public long id() {
        return accountBean != null ? accountBean.getUid() : -1L;
    }

    @Override
    public void sessionActive() {
        super.sessionActive();
    }

    @Override
    public void sessionInactive() {
        super.sessionInactive();
    }

    /**
     *
     */
    public PlayerCallback userClient() {
        if (!isOnline() || proxy() == null)
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
