package com.exporum.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public enum AuthStatus {

    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "auth.unauthorized", "Unauthorized"),
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, "auth.jwt.expired", "토큰이 만료되었습니다." ),
    INVALID_TOKEN_SIGNATURE(HttpStatus.FORBIDDEN, "auth.invalid.token", "잘못된 토큰 형식입니다.");


    private HttpStatus httpStatus;
    private String code;
    private String message;
}
