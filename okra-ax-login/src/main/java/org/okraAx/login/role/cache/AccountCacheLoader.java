package org.okraAx.login.role.cache;

import org.ogcs.app.AppContext;
import org.okraAx.login.bean.AccountBean;
import org.okraAx.login.bean.RoleBean;
import org.okraAx.login.role.mybatis.AccountMapper;
import org.okraAx.login.role.mybatis.RoleMapper;
import org.okraAx.login.server.LoginUser;

/**
 * @author TinyZ.
 * @version 2017.05.14
 */
public final class AccountCacheLoader extends com.google.common.cache.CacheLoader<String, LoginUser> {

    private AccountMapper accountMapper = AppContext.getBean(AccountMapper.class);
    private RoleMapper roleMapper = AppContext.getBean(RoleMapper.class);

    @Override
    public LoginUser load(String openId) throws Exception {
        LoginUser user = new LoginUser();
        //  account
        AccountBean bean = accountMapper.selectByOpenId(openId);
        if (bean != null) {
            user.setAccountBean(bean);
            //  role
            RoleBean roleBean = roleMapper.select(bean.getUid());
            if (roleBean != null) {
                //  initialize
                user.setRoleBean(roleBean);
//                    user.lazyLoad();
            }
        }
        return user;
    }
}