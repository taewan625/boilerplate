package com.exporum.client.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 8.
 * @description :
 */
public class VerifyEmailException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6749688290698735467L;


    public VerifyEmailException() {
        super("Data not found");
    }

    public VerifyEmailException(String message) {
        super(message);
    }

}
