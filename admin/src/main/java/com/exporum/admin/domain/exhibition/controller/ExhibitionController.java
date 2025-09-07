package com.exporum.admin.domain.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * ExhibitionController.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 7. 최초 작성
 */
@Controller
public class ExhibitionController {
    //전시 목록 url
    @GetMapping("/exhibition/exhibitions")
    public String exhibition() {
        return "exhibition/exhibitions";
    }

    //전시 담당자 url
    @GetMapping("/exhibition/managers")
    public String assignments() {
        return "exhibition/managers";
    }

    //전시 권한 url
    @GetMapping("/exhibition/roles")
    public String roles() {
        return "exhibition/roles";
    }
}
