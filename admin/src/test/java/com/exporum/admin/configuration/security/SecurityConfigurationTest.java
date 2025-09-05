package com.exporum.admin.configuration.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class SecurityConfigurationTest {

    @Test
    void crypto(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        String rawPassword = "1";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println(">>>>>>>>>>>>>>>>>"+encodedPassword);
    }

}