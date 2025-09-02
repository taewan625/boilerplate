package com.exporum.client.domain.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Kwon taewan
 * @date : 2025. 06. 17.
 * @description :
 */

@Controller
public class HomeController {
    @GetMapping("/")
    public String homeController(){
        return "home/home";
    }
}
