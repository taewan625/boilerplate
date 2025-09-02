package com.exporum.admin.domain.booth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Lee Hyunseung
 * @date : 25. 5. 13.
 * @description :
 */

@Controller
public class BoothController {

    @GetMapping("/booth-request")
    public String boothRequest() {
        return "booth/booth";
    }

    @GetMapping("/booth-request/{id}")
    public String boothRequest(@PathVariable String id) {
        return "booth/booth-detail";
    }



}
