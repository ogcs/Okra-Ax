package org.okraAx.login.role.cache;

import org.ogcs.app.AppContext;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.mybatis.AccountMapper;
import org.okraAx.login.role.mybatis.RoleMapper;
import org.okraAx.login.server.User;

/**
 * @author TinyZ.
 * @version 2017.05.14
 */
public final class AccountCacheLoader extends com.google.common.cache.CacheLoader<String, User> {

    private AccountMapper accountMapper = AppContext.getBean(AccountMapper.class);
    private RoleMapper roleMapper = AppContext.getBean(RoleMapper.class);

    @Override
    public User load(String openId) throws Exception {
        User user = new User();
        //  account
        AccountBean bean = accountMapper.selectByOpenId(openId);
        if (bean != null) {
            user.setAccountBean(bean);
            //  role
            RoleBean roleBean = roleMapper.select(bean.getUid());
            if (roleBean != null) {
                //  initialize
                user.setRoleBean(roleBean);
                initUserData(user);
            }
        }
        return user;
    }

    /**
     * 初始化User
     */
    private void initUserData(User user) {

    }

}