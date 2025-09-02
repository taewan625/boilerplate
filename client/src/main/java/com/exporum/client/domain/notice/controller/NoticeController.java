package com.exporum.client.domain.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Kwon taewan
 * @date : 2025. 06. 17.
 * @description :
 */

@Controller
public class NoticeController {
    @GetMapping("/notice")
    public String notice() {
        return "notice/notice-common";
    }

    @GetMapping("/press-center")
    public String pressCenter() {
        return "notice/notice-common";
    }

    @GetMapping("/newsletter/subscribe")
    public String newsletterSubscribe() {
        return "notice/subscribe";
    }

    @GetMapping({"/notice/{id}", "/press-center/{id}"})
    public String noticeCommonDetail(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        return "notice/notice-common-detail";
    }

    @GetMapping("/newsletter")
    public String newsletter() {
        return "notice/newsletter";
    }

    @GetMapping("/newsletter/{id}")
    public String newsletterDetail(@PathVariable int id, Model model) {
        model.addAttribute("id", id);
        return "notice/newsletter-detail";
    }
}
