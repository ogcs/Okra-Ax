package org.okraAx.login.component;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.Session;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.cache.AccountCacheLoader;
import org.okraAx.login.role.mybatis.AccountMapper;
import org.okraAx.login.role.mybatis.RoleMapper;
import org.okraAx.login.role.mybatis.UserClient;
import org.okraAx.login.server.LoginUser;
import org.okraAx.login.util.LoginConfig;
import org.okraAx.utilities.SessionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author TinyZ.
 * @version 2017.03.25
 */
@Component
public class LoginComponent {

    private static final Logger LOG = LogManager.getLogger(LoginComponent.class);

    @Autowired
    private TransactionTemplate txTemplate;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RoleMapper roleMapper;

    private LoadingCache<String, LoginUser> accountCache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(1L, TimeUnit.HOURS)
            .removalListener(new RemovalListener<String, LoginUser>() {
                @Override
                public void onRemoval(RemovalNotification<String, LoginUser> notification) {

                }
            })
            .build(new AccountCacheLoader());

    private void initComponent() {
        //  load account and role data from database

    }


    public void onCreateRole(final Session session, final String openId, final String name,
                             final int figure) {
        try {
            LoginUser user = accountCache.get(openId);
            user.setSession(session);
            session.setConnector(user);
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
                        accountCache.put(openId, user);

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
     * 登录
     *
     * @param openId 平台用户名
     */
    public void onLogin(String openId) {
        Session session = SessionHelper.currentSession();
        //  TODO: 授权验证
        try {
            LoginUser user = accountCache.get(openId);
            user.setSession(session);
            session.setConnector(user);
            if (user.id() < 0L) {
                user.userClient().callbackLogin(-1);
                return;
            }
            user.userClient().callbackLogin(0);
        } catch (ExecutionException e) {
            LOG.info("[onLogin] exception.", e);
        }
    }

    public void onSyncTime() {
        Session session = SessionHelper.currentSession();
        UserClient user = new UserClient(session);//SessionHelper.curPlayer();
        if (user.isOnline()) {
            user.proxy().callbackSyncTime(System.currentTimeMillis());
        }
    }

    public void onShowChannelList() {

    }

    public void onEnterChannel() {

    }


}
