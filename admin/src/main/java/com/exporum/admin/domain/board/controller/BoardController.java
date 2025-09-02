package com.exporum.admin.domain.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Controller
public class BoardController {

    @GetMapping("/board/notice")
    public String notice() {
        return "board/notice/notice";
    }

    @GetMapping("/board/notice/{id}")
    public String noticeDetail(@PathVariable long id) {
        return "board/notice/notice-detail";
    }

    @GetMapping("/board/notice/{id}/modify")
    public String noticeModify(@PathVariable long id) {
        return "board/notice/notice-modify";
    }

    @GetMapping("/board/notice/register")
    public String noticeRegistry() {
        return "board/notice/notice-write";
    }



    @GetMapping("/board/press")
    public String press(){
        return "board/press/press";
    }

    @GetMapping("/board/press/{id}")
    public String pressDetail(@PathVariable long id) {
        return "board/press/press-detail";
    }

    @GetMapping("/board/press/{id}/modify")
    public String pressModify(@PathVariable long id) {
        return "board/press/press-modify";
    }

    @GetMapping("/board/press/register")
    public String pressRegistry() {
        return "board/press/press-write";
    }



    @GetMapping("/board/contactus")
    public String contactus(){
        return "board/contactus/contactus";
    }

    @GetMapping("/board/contactus/{id}")
    public String contactusDetail(@PathVariable long id) {return "board/contactus/contactus-detail";}



    @GetMapping("/board/newsletter")
    public String newsletter(){return "/board/newsletter/newsletter";}
    @GetMapping("/board/newsletter/{id}")
    public String newsletterDetail(@PathVariable long id) {return "board/newsletter/newsletter-detail";}
    @GetMapping("/board/newsletter/{id}/modify")
    public String newsletterModify(@PathVariable long id) {return "board/newsletter/newsletter-modify";}
    @GetMapping("/board/newsletter/register")
    public String newsletterRegistry() {return "board/newsletter/newsletter-write";}

}
