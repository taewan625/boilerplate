package com.exporum.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 20.
 * @description :
 */

@Getter
@RequiredArgsConstructor
public enum OperationStatus {
    SUCCESS(true, "Success"),
    FAILURE(false, "Failure");

    private final boolean success;
    private final String message;

}
