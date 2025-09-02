package com.exporum.core.domain.order.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 9.
 * @description :
 */

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    PAYMENT_CANCELLATION( "Payment Cancellation"),
    PAYMENT_PENDING( "Payment Pending"),
    PAYMENT_COMPLETED( "Payment Completed"),
    REFUND_PENDING( "Refund Pending"),
    REFUND_COMPLETED( "Refund Completed");

    private final String status;

}
