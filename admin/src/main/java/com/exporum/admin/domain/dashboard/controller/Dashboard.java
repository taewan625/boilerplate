package com.exporum.admin.domain.dashboard.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Dashboard.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 5. 최초 작성
 */
@Controller
public class Dashboard {
    @GetMapping(value = {"/"})
    public String redirectDashboard() {
        return "redirect:/dashboard";
    }

    @GetMapping(value = {"/dashboard"})
    public String dashboard() {
        return "dashboard/dashboard";
    }
}