package org.okraAx.login.role.mybatis;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.okraAx.internal.mybatis.OkraAxDB;
import org.okraAx.login.bean.RoleBean;

/**
 * @author TinyZ.
 * @version 2017.05.12
 */
@OkraAxDB
public interface RoleMapper {

    @Select("select * from t_role where uid=#{uid}")
    RoleBean select(long uid);

    @Insert("INSERT INTO `t_role` (`uid`, `name`, `figure`, `silver`) VALUES " +
            "(#{uid}, #{name}, #{figure}, #{silver});")
    @Options(useGeneratedKeys = true, keyProperty = "rid")
    void insert(RoleBean bean);

    @Update("UPDATE `t_role` SET `name`=#{name}, `figure`=#{figure}, `silver`=#{silver} WHERE uid=#{uid}")
    void update(RoleBean bean);
}
