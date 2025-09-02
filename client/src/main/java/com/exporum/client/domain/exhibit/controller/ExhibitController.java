package com.exporum.client.domain.exhibit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.exceptions.TemplateInputException;

/**
 * @author: Kwon taewan
 * @date : 2025. 06. 17.
 * @description :
 */

@Controller
//@RequestMapping("/exhibit")
public class ExhibitController {

    @GetMapping("/partners")
    public String partners(){
        return "exhibit/partners";
    }

    @GetMapping("/exhibitor-list")
    public String exhibitorList(){
        return "exhibit/exhibitor-list";
    }

    @GetMapping("/floor-plan")
    public String floorPlan(){
        return "exhibit/floor-plan";
    }

    @GetMapping("/exhibitor-application")
    public String exhibitorApplication() throws TemplateInputException {
        return "exhibit/exhibitor-application";
    }

    @GetMapping("/exhibitor-profile")
    public String exhibitorProfile(){
        return "redirect:/exhibitor-inquiry";
    }

    @GetMapping("/exhibitor-inquiry")
    public String exhibitorInquiry(){
        return "exhibit/exhibitor-inquiry";
    }
}
