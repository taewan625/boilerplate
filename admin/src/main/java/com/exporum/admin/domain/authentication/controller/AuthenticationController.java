package com.exporum.admin.domain.authentication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@Controller
public class AuthenticationController {
    @GetMapping("/login")
    public String login(Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        return "authentication/login";
    }
}
