package com.exporum.core.response;

import com.exporum.core.enums.OperationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */


@Getter
@Setter
@AllArgsConstructor
public class OperationResponse {
    private boolean success;
    private String message;

    public OperationResponse(OperationStatus operationStatus) {
        this.success = operationStatus.isSuccess();
        this.message = operationStatus.getMessage();
    }
}
