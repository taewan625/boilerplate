package com.exporum.admin.domain.popup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 3. 6.
 * @description :
 */

@Controller
@RequiredArgsConstructor
public class PopupController {

    @GetMapping("/popup")
    public String exhibitor() {
        return "popup/popup";
    }

}
