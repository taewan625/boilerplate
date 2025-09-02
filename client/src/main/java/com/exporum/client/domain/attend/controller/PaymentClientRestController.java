package com.exporum.client.domain.attend.controller;

import com.exporum.client.domain.attend.service.PaymentClientService;
import com.exporum.core.enums.OperationStatus;
import com.exporum.core.response.ContentsResponse;
import com.exporum.core.response.OperationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * PaymentClientRestController.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 7. 30.
 * @description: todo. getPaymentInfo 현재 임시로 사용하는 메서드. 향후 결제 수단이 다건으로 변경됨에 따라 로직 수정 필요 및 core 쪽으로 패키지 이동 필요
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/${application.api.version}")
public class PaymentClientRestController {


    private final PaymentClientService paymentClientService;

    @GetMapping("/payment-info")
    public ResponseEntity<OperationResponse> getPaymentInfo() {
        return ResponseEntity.ok(new ContentsResponse<>(OperationStatus.SUCCESS, paymentClientService.getPaymentInfo()));
    }
}
