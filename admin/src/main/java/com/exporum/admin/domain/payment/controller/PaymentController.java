package com.exporum.admin.domain.payment.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 10.
 * @description :
 */

@Controller
public class PaymentController {

    @GetMapping("/payment")
    public String payment() {
        return "payment/payment";
    }
}
