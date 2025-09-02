package com.exporum.client.domain.attend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author: Kwon taewan
 * @date : 2025. 06. 17.
 * @description :
 */

@Controller
public class AttendController {
    @GetMapping("/show-guide")
    public String showGuide() {
        return "attend/show-guide";
    }

    @GetMapping("/visitor")
    public String visitor() {
        return "attend/visitor";
    }

    @GetMapping("/payment")
    public String payment() {
        return "attend/payment";
    }

    @PostMapping("/payment/detail")
    public String paymentDetail(@RequestParam String merchantUid, RedirectAttributes redirectAttributes) {
        //addAttribute 사용시, url에 body 데이터 표출 - addFlashAttribute로 숨김 처리
        redirectAttributes.addFlashAttribute("merchantUid", merchantUid);

        return "redirect:/payment/detail";
    }

    @GetMapping("/payment/detail")
    public String paymentDetailView(Model model) {
        Object merchantUid = model.getAttribute("merchantUid");
        return (merchantUid == null || merchantUid.toString().isEmpty()) ? "redirect:/visitor" : "attend/payment-detail";
    }
}
