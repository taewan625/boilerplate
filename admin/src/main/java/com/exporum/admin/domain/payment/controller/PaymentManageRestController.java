package com.exporum.admin.domain.payment.controller;

import com.exporum.admin.domain.payment.model.PageablePayment;
import com.exporum.admin.domain.payment.service.PaymentManageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 24.
 * @description :
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.admin}")
public class PaymentManageRestController {

    private final PaymentManageService paymentManageService;

    @PostMapping("/payment")
    public PageablePayment getPageablePayment(PageablePayment search) {
        paymentManageService.getPageablePayment(search);
        return search;
    }

    @GetMapping("/payment/excel/{exhibitionId}")
    public Callable<ResponseEntity<StreamingResponseBody>> excelDownloadTicketPayment(@PathVariable long exhibitionId, HttpServletResponse response) throws IOException {
        return () -> paymentManageService.excelDownload(exhibitionId,response);
    }
}
