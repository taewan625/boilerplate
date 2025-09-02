package com.exporum.core.response;

import com.exporum.core.enums.OperationStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class ContentsResponse<T> extends OperationResponse{

    private T data;

    public ContentsResponse(OperationStatus operationStatus) {
        super(operationStatus.isSuccess(), operationStatus.getMessage());
    }

    public ContentsResponse(OperationStatus operationStatus, T data) {
        super(operationStatus.isSuccess(), operationStatus.getMessage());
        this.data = data;
    }

    public void seResponse(OperationStatus operationStatus, T data) {
        super.setSuccess(operationStatus.isSuccess());
        super.setMessage(operationStatus.getMessage());
        this.data = data;
    }
}
