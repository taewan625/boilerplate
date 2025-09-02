package com.exporum.client.auth.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */

@Getter
@Setter
public class VerifyEmail {

    private String email;
    private String code;


}
