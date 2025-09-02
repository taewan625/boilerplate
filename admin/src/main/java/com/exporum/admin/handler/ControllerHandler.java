package com.exporum.admin.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@ControllerAdvice
public class ControllerHandler {
    @ModelAttribute("currentUrl")
    public String getCurrentUrl(HttpServletRequest request){
        return request.getServletPath();
    }
}
