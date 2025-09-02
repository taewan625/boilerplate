package com.exporum.client.auth.mapper;

import com.exporum.client.auth.model.EmailVerification;
import com.exporum.client.auth.model.VerifyEmail;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Mapper
public interface EmailVerificationMapper {

    @InsertProvider(type = EmailVerificationSqlProvider.class, method = "insertEmailVerification")
    int insertEmailVerification(@Param("emailVerification") EmailVerification emailVerification);


    @SelectProvider(type = EmailVerificationSqlProvider.class, method = "getEmailVerification")
    EmailVerification getEmailVerification(@Param("verifyEmail") VerifyEmail verifyEmail);
}
