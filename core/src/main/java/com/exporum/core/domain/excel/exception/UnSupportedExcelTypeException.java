package com.exporum.core.domain.excel.exception;

import com.exporum.core.exception.ExcelException;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class UnSupportedExcelTypeException extends ExcelException {

    public UnSupportedExcelTypeException(String message) {
        super(message, null);
    }
}
