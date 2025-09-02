package com.exporum.admin.helper;

import com.exporum.admin.auth.model.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 21.
 * @description :
 */
public class AuthenticationHelper {

    public static long getAuthenticationUserId(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        AuthUser authUser = (AuthUser) authentication.getPrincipal();

        return authUser.getId();
    }
}
