package com.exporum.client.domain.about.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Kwon taewan
 * @date : 2025. 06. 17.
 * @description :
 */

@Controller
//@RequestMapping("/about")
public class AboutController {

    @GetMapping("/show-info")
    public String showInfoController(){
        return "about/show-info";
    }

    @GetMapping("/schedule")
    public String scheduleController(){
        return "about/schedule";
    }

    @GetMapping("/venue")
    public String venueController(){
        return "about/venue";
    }

    @GetMapping("/contact-us")
    public String contactUsController(){
        return "about/contact-us";
    }
}
