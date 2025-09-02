package com.exporum.client.domain.attend.service;

import com.exporum.client.domain.attend.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


/**
 * PaymentClientService.java
 *
 * @author: Kwon Taewan
 * @date: 2025. 7. 30.
 * @description:
 */
@Service
public class PaymentClientService {
    @Value("${payment.imp.init}")
    private String init;

    @Value("${payment.imp.pg}")
    private String pg;

    public Payment getPaymentInfo() {
        Payment payment = new Payment();
        payment.setInit(init);
        payment.setPg(pg);

        return payment;
    }
}
