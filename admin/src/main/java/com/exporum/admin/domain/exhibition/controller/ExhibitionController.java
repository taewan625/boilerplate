package com.exporum.admin.domain.exhibition.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@Controller
public class ExhibitionController {

    @GetMapping("/exhibition/schedule")
    public String schedule() {
        return "exhibition/schedule/schedule";
    }

    @GetMapping("/exhibition/schedule/{id}")
    public String scheduleInfo(@PathVariable("id")long id) {
        return "exhibition/schedule/schedule-detail";
    }

    @GetMapping("/exhibition/schedule/register")
    public String scheduleRegistry() {
        return "exhibition/schedule/schedule-write";
    }

    @GetMapping("/exhibition/schedule/{id}/modify")
    public String scheduleModify(@PathVariable("id")long id) {
        return "exhibition/schedule/schedule-modify";
    }

    @GetMapping("/exhibition/floor-plan")
    public String floorPlan() {
        return "exhibition/floor-plan/floor-plan";
    }

    @GetMapping("/exhibition/ticket")
    public String ticket() {
        return "exhibition/ticket/ticket";
    }

    @GetMapping("/exhibition/ticket/{id}")
    public String ticketInfo(@PathVariable("id")long id) {
        return "exhibition/ticket/ticket-detail";
    }

    @GetMapping("/exhibition/ticket/register")
    public String ticketRegistry() {
        return "exhibition/ticket/ticket-write";
    }

    @GetMapping("/exhibition/ticket/{id}/modify")
    public String ticketModify(@PathVariable("id")long id) {
        return "exhibition/ticket/ticket-modify";
    }

}
