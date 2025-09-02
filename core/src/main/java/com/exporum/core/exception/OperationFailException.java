package com.exporum.core.exception;

import lombok.Getter;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 6.
 * @description :
 */

@Getter
public class OperationFailException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7678313238808225406L;


    public OperationFailException() {
        super("Operation failed");
    }

    public OperationFailException(String message) {
        super(message);
    }

}
