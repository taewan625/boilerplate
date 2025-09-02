package com.exporum.admin.domain.subscribe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Controller
public class SubscribeController {


    @GetMapping("/subscribe")
    public String subscribe() {
        return "subscribe/subscribe";
    }
}
