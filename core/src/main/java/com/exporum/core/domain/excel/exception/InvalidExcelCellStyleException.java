package com.exporum.core.domain.excel.exception;

import com.exporum.core.exception.ExcelException;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 3.
 * @description :
 */
public class InvalidExcelCellStyleException extends ExcelException {

    public InvalidExcelCellStyleException(String message, Throwable cause) {
        super(message, cause);
    }

}
