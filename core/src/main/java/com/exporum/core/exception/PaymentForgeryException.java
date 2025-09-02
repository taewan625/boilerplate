package com.exporum.core.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 15.
 * @description :
 */
public class PaymentForgeryException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -7483582240615978480L;

    public PaymentForgeryException() {
        super("Forgery detected in the payment request.");
    }

    public PaymentForgeryException(String message) {
        super(message);
    }

}
