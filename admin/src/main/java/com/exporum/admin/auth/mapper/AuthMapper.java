package com.exporum.admin.auth.mapper;

import com.exporum.admin.auth.model.AuthUser;
import com.exporum.admin.auth.model.ChangePassword;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Mapper
public interface AuthMapper {

    @SelectProvider(type = AuthSqlProvider.class, method = "getUser")
    AuthUser getUser(@Param("username") String username);

    @SelectProvider(type = AuthSqlProvider.class, method = "getChangePassword")
    AuthUser getChangePassword(@Param("id") long id);

    @UpdateProvider(type = AuthSqlProvider.class, method = "updateLastLoginTime")
    void updateLastLoginTime(@Param("username") String username);

    @UpdateProvider(type = AuthSqlProvider.class, method = "changePassword")
    int changePassword(@Param("id") long id, @Param("changePassword") ChangePassword changePassword);

}
