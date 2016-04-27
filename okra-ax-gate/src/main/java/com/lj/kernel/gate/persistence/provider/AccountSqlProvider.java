package com.lj.kernel.gate.persistence.provider;

import com.lj.kernel.gate.persistence.domain.MemAccount;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author : TinyZ.
 * @email : ogcs_tinyz@outlook.com
 * @date : 2016/3/31
 */
public class AccountSqlProvider {

    private static final String TABLE_NAME = "tb_account";

    public String insertSql(final MemAccount memAccount) {
        return new SQL(){{
            INSERT_INTO(TABLE_NAME);
            VALUES("uid", "#{uid}");
            VALUES("name", "#{name}");
            VALUES("figure", "#{figure}");
            VALUES("account", "#{account}");
            VALUES("psw", "#{psw}");
            VALUES("email", "#{email}");
            VALUES("phone", "#{phone}");
            VALUES("channel", "#{channel}");
            VALUES("platform", "#{platform}");
            VALUES("timeCreate", "#{timeCreate}");
            VALUES("timeLastLogin", "#{timeLastLogin}");
            VALUES("ipCreate", "#{ipCreate}");
            VALUES("ipLastLogin", "#{ipLastLogin}");
            VALUES("vipLevel", "#{vipLevel}");
            VALUES("vipExp", "#{vipExp}");
            VALUES("rmb", "#{rmb}");
        }}.toString();
    }

    public String deleteSql() {
        return new SQL(){{
            DELETE_FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String updateSql() {
        return new SQL(){{
            UPDATE(TABLE_NAME);
            SET("uid=#{uid}");
            SET("name=#{name}");
            SET("figure=#{figure}");
            SET("account=#{account}");
            SET("psw=#{psw}");
            SET("email=#{email}");
            SET("phone=#{phone}");
            SET("channel=#{channel}");
            SET("platform=#{platform}");
            SET("timeLastLogin=#{timeLastLogin}");
            SET("ipLastLogin=#{ipLastLogin}");
            SET("vipLevel=#{vipLevel}");
            SET("vipExp=#{vipExp}");
            SET("rmb=#{rmb}");
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String selectByUidSql() {
        return new SQL(){{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("uid=#{uid}");
        }}.toString();
    }

    public String selectByPhoneSql() {
        return new SQL(){{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("phone=#{phone}");
        }}.toString();
    }

    public String selectByEmailSql() {
        return new SQL(){{
            SELECT("*");
            FROM(TABLE_NAME);
            WHERE("email=#{email}");
        }}.toString();
    }
}
