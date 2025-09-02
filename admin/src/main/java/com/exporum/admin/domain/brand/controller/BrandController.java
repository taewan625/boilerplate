package com.exporum.admin.domain.brand.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Controller
public class BrandController {


    @GetMapping("/exhibition/brand")
    public String exhibitor() {
        return "exhibition/brand/brand";
    }

    @GetMapping("/exhibition/brand/{id}")
    public String exhibitor(@PathVariable long id) {
        return "exhibition/brand/brand-detail";
    }

    @GetMapping("/exhibition/brand/{id}/modify")
    public String exhibitorModify(@PathVariable long id) {
        return "exhibition/brand/brand-modify";
    }
}
