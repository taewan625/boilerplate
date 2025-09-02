package com.exporum.core.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 17.
 * @description :
 */
public class FileNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6749688290698735467L;


    public FileNotFoundException() {
        super("File not found");
    }

    public FileNotFoundException(String message) {
        super(message);
    }
}
