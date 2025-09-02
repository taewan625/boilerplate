package com.exporum.admin.domain.dashbord.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@Controller
@RequiredArgsConstructor
public class DashboardController {

    @GetMapping(value = {"/"})
    public String redirectDashboard() {
        return "redirect:/dashboard";
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard() {
        return "dashboard/dashboard";
    }
}
