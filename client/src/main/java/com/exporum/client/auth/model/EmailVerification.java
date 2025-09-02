package com.exporum.client.auth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Getter
@Setter
public class EmailVerification {


    private String code;
    private String email;
    private String expiredAt;


    @Builder
    public EmailVerification(String code, String email, String expiredAt) {
        this.code = code;
        this.email = email;
        this.expiredAt = expiredAt;
    }

}
