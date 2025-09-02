package com.exporum.admin.auth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 6.
 * @description :
 */

@Controller
public class AuthController {

    @GetMapping(value = {"/login"})
    public String login(){
        return "authentication/login";
    }


//    @GetMapping(value = {"/logout"})
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        new SecurityContextLogoutHandler().logout(request, response, auth);
//        return "authentication/login";
//    }
}
