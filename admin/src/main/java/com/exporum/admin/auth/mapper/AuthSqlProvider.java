package com.exporum.admin.auth.mapper;

import com.exporum.admin.auth.model.ChangePassword;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */
public class AuthSqlProvider {

    public String getUser(String username) {
        return new SQL(){
            {
                SELECT("""
                       a.id, a.role_code as role, c.code_name as role_name,
                       a.email, a.password, a.admin_name as name, a.phone_number as mobile_number,
                       is_initial_password as initial_password, a.is_block as blocked, a.last_login_at,
                       CASE
                           WHEN a.password_expire_date < NOW() THEN 1
                           ELSE 0
                       END as expiration
                       """);
                FROM("admin as a");
                JOIN("code as c on c.code = a.role_code");
                WHERE("a.email = #{username}");
                WHERE("a.is_deleted = 0");
            }
        }.toString();
    }

    public String updateLastLoginTime(String username) {
        return new SQL(){
            {
                UPDATE("admin");
                SET("last_login_at = sysdate()");
                WHERE("email = #{username}");
            }
        }.toString();
    }

    public String getChangePassword(long id) {
        return new SQL(){
            {
                SELECT("""
                       a.id, a.role_code as role, c.code_name as role_name,
                       a.email, a.password, a.admin_name as name, a.phone_number as mobile_number,
                       is_initial_password as initial_password, a.is_block as blocked, a.last_login_at,
                       CASE
                           WHEN a.password_expire_date < NOW() THEN 1
                           ELSE 0
                       END as expiration
                       """);
                FROM("admin as a");
                JOIN("code as c on c.code = a.role_code");
                WHERE("a.id = #{id}");
                WHERE("a.is_deleted = 0");
            }
        }.toString();
    }

    public String changePassword(long id, ChangePassword changePassword) {
        return new SQL(){
            {
                UPDATE("admin");
                SET("password = #{changePassword.newPassword}");
                SET("is_initial_password = 0");
                SET("updated_at = sysdate()");
                SET("updated_by = #{id}");
                WHERE("id = #{id}");
            }
        }.toString();
    }
}
