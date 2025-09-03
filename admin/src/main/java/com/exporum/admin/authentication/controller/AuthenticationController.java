package com.exporum.admin.authentication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@Controller
public class AuthenticationController {
    @GetMapping(value = {"/login"})
    public String login(){
        return "authentication/login";
    }
}
