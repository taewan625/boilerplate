package com.exporum.client.auth.mapper;

import com.exporum.client.auth.model.EmailVerification;
import com.exporum.client.auth.model.VerifyEmail;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
public class EmailVerificationSqlProvider {


    public String insertEmailVerification(EmailVerification emailVerification) {
        return new SQL(){
            {
                INSERT_INTO("email_verification");
                VALUES("code", "#{emailVerification.code}");
                VALUES("email", "#{emailVerification.email}");
                VALUES("expired_at", "#{emailVerification.expiredAt}");
            }
        }.toString();
    }


    public String getEmailVerification(VerifyEmail verifyEmail) {
        return new SQL(){
            {
                SELECT("""
                        code, email, expired_at
                        """);
                FROM("email_verification");
                WHERE("code = #{verifyEmail.code}");
                WHERE("email = #{verifyEmail.email}");
                ORDER_BY("created_at DESC");
                LIMIT(1);
            }
        }.toString();
    }
}
