package org.okraAx.logic.persistence.mapper;

import org.okraAx.logic.persistence.domain.MemAccount;
import org.okraAx.logic.persistence.provider.AccountSqlProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

public interface AccountMapper {

    @InsertProvider(type = AccountSqlProvider.class, method = "insertSql")
    void insert(MemAccount memAccount);

    @UpdateProvider(type = AccountSqlProvider.class, method = "deleteSql")
    void deleteByUid(long uid);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateSql")
    MemAccount update(MemAccount memAccount);

    @UpdateProvider(type = AccountSqlProvider.class, method = "updateSql")
    void updateByFields(MemAccount memAccount);

    @SelectProvider(type = AccountSqlProvider.class, method = "selectByUidSql")
    MemAccount selectByUid(long uid);

    @SelectProvider(type = AccountSqlProvider.class, method = "selectByPhoneSql")
    MemAccount selectByPhone(long account);

    @SelectProvider(type = AccountSqlProvider.class, method = "selectByEmailSql")
    MemAccount selectByEmail(String email);
}
