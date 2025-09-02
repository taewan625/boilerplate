package com.exporum.core.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 15.
 * @description :
 */



public class ThirdPartyApiException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1414914898070024120L;

    public ThirdPartyApiException() {
        super("Third party API call exception");
    }

    public ThirdPartyApiException(String message) {
        super(message);
    }
}
