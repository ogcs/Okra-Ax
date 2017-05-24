package org.okraAx.login.role.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.okraAx.internal.mybatis.OkraAxDB;
import org.okraAx.login.bean.AccountBean;

/**
 * @author TinyZ.
 * @version 2017.05.12
 */
@OkraAxDB
public interface AccountMapper {

    @Select("select * from t_account where openId=#{openId}")
    AccountBean selectByOpenId(String openId);

    @Select("select * from t_account where uid=#{uid}")
    AccountBean select(long uid);

    @Insert("INSERT INTO `t_account` (`uid`, `openId`, `timeCreateAccount`, `ip`, `lastLoginTime`, `lastLogoutTime`, `lastLoginIP`, `status`, `identify`, `gold`, `totalGold`) VALUES" +
            " (#{uid}, #{openId}, #{timeCreateAccount}, #{ip}, #{lastLoginTime}, #{lastLoginTime}, #{lastLoginIP}, #{status}, #{identify}, #{gold}, #{totalGold});")
    void insert(AccountBean bean);

    @Update("UPDATE `t_account` SET `lastLoginTime`=#{lastLoginTime}, `lastLogoutTime`=#{lastLogoutTime}, `lastLoginIP`=#{lastLoginIP}," +
            " `status`=#{status}, `identify`=#{identify}, `gold`=#{gold}, `totalGold`=#{totalGold} WHERE (`uid`=#{uid});")
    void update(AccountBean bean);
}