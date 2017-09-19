package org.okraAx.login.component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.okraAx.internal.net.NetSession;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.cache.AccountCacheLoader;
import org.okraAx.login.role.mybatis.AccountMapper;
import org.okraAx.login.role.mybatis.RoleMapper;
import org.okraAx.login.server.User;
import org.okraAx.login.util.LoginConfig;
import org.okraAx.utilities.NetHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.08.26
 */
@Component
public class UserComponent {

    private static final Logger LOG = LogManager.getLogger(UserComponent.class);
    @Autowired
    private TransactionTemplate txTemplate;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleMapper roleMapper;

    private LoadingCache<String, User> loginUserCache = CacheBuilder
            .newBuilder()
            .expireAfterAccess(1L, TimeUnit.HOURS)
            .removalListener(new RemovalListener<String, User>() {
                @Override
                public void onRemoval(RemovalNotification<String, User> notification) {

                    session2userMap.remove(notification.getValue().session());


                }
            })
            .build(new AccountCacheLoader());

    private Map<NetSession, User> session2userMap = new ConcurrentHashMap<>();
    private Map<Long, User> uid2userMap = new ConcurrentHashMap<>();

    /**
     * 登录
     */
    public User onLogin(String openId) {
        User user = null;
        try {
            user = loginUserCache.get(openId);
            if (user != null) {
                NetSession session = NetHelper.session();
                session2userMap.put(session, user);
                uid2userMap.put(user.id(), user);
                //  initialize
                user.setSession(session);
                user.lazyLoad();
                user.userClient().callbackLogin(0);
            }
        } catch (ExecutionException e) {
            LOG.error(e.getMessage(), e);
        }
        return user;
    }

    /**
     * 创建角色
     *
     * @param openId 开放ID
     * @param name   角色名
     * @param figure 形象
     */
    public void onCreateRole(final NetSession session, final String openId, final String name,
                             final int figure) {
        try {
            User user = loginUserCache.get(openId);
            if (user.id() > 0L) {
                LOG.info("[onCreateRole] the role[openId:" + openId + ", name:" + name + "] is exist. ");
                user.userClient().callbackCreateRole(-1);
                return;
            }
            String host = ((InetSocketAddress) session.channel().remoteAddress()).getHostString();
            final AccountBean bean = new AccountBean();
            bean.setUid(LoginConfig.id());
            bean.setOpenId(openId);
            bean.setIp(host);
            bean.setLastLoginIP(host);
            bean.setTimeCreateAccount(System.currentTimeMillis());
            //
            this.txTemplate.execute(new TransactionCallback<Object>() {
                @Override
                public Object doInTransaction(TransactionStatus transactionStatus) {
                    try {
                        //  insert t_account
                        accountMapper.insert(bean);
                        //  insert t_role
                        RoleBean roleBean = new RoleBean();
                        roleBean.setName(name);
                        roleBean.setUid(bean.getUid());
                        roleBean.setFigure(figure);
                        roleMapper.insert(roleBean);
                        //
                        user.setAccountBean(bean);
                        user.setRoleBean(roleBean);
                        loginUserCache.put(openId, user);
                        session2userMap.put(session, user);
                        uid2userMap.put(user.id(), user);

                        user.userClient().callbackCreateRole(0);
                    } catch (Exception e) {
                        transactionStatus.setRollbackOnly();
                        LOG.error("create role failed. openId:" + openId, e);
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            LOG.info("[onCreateRole] exception.", e);
        }
    }

    /**
     * 断线
     */
    public void onDisconnect(NetSession session) {
        User user = session2userMap.remove(session);
        if (user != null) {
            uid2userMap.remove(user.id());
            user.disconnect();
        }
    }

    public User getUserByUid(long uid) {
        return uid2userMap.get(uid);
    }

    public User getUserBySession(NetSession session) {
        return session2userMap.get(session);
    }

    /**
     * 获取系统时间
     */
    public void onSyncTime() {
        User user = getUserBySession(NetHelper.session());
        if (user != null) {
            user.proxy().callbackSyncTime(System.currentTimeMillis());
        }
    }
}
