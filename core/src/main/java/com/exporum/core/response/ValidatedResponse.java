package com.exporum.core.response;

import com.exporum.core.enums.OperationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 19.
 * @description :
 */

@Getter
@Setter
public class ValidatedResponse<T> extends OperationResponse{

    private List<Map<String,String>> errors;

    public ValidatedResponse(OperationStatus operationStatus) {
        super(operationStatus.isSuccess(), operationStatus.getMessage());
    }

    public ValidatedResponse(OperationStatus operationStatus,  List<Map<String,String>> errors) {
        super(operationStatus.isSuccess(), operationStatus.getMessage());
        this.errors = errors;
    }

    public void seResponse(OperationStatus operationStatus,  List<Map<String,String>> errors) {
        super.setSuccess(operationStatus.isSuccess());
        super.setMessage(operationStatus.getMessage());
        this.errors = errors;
    }
}
