package com.exporum.core.exception;

import java.io.Serial;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 27.
 * @description :
 */
public class DataNotFoundException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 6749688290698735467L;


    public DataNotFoundException() {
        super("Data not found");
    }

    public DataNotFoundException(String message) {
        super(message);
    }


}

