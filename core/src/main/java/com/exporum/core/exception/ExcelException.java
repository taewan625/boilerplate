package com.exporum.core.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class ExcelException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 3386701631381006784L;

    public ExcelException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExcelException() {
        super("Excel exception");
    }

    public ExcelException(String message) {
        super(message);
    }
}
