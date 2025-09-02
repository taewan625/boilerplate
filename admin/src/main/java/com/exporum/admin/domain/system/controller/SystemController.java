package com.exporum.admin.domain.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Controller
public class SystemController {

    @GetMapping("/system/admin")
    public String admin() {
        return "system/admin";
    }

    @GetMapping("/system/user")
    public String user(){
        return "system/user";
    }

    @GetMapping("/system/code")
    public String code(){
        return "system/code";
    }
}
