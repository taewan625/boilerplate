package com.exporum.admin.handler;

import com.exporum.core.enums.OperationStatus;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.response.OperationResponse;
import com.exporum.core.response.ValidatedResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {

//        OperationResponse response =new OperationResponse(OperationStatus.FAILURE);
//        response.setMessage(ex.getMessage());
        ex.printStackTrace();

        return "error/not-found-error";
    }

    @ExceptionHandler(TemplateInputException.class)
    public String handleTemplateInputExceptionException(TemplateInputException ex) {

        return "error/runtime-error";
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex){
        return "error/not-found-error";
    }


    @ExceptionHandler(OperationFailException.class)
    public ResponseEntity<OperationResponse> handleException(OperationFailException ex) {

        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());

        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidatedResponse<List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(error.getField(), Objects.requireNonNull(error.getDefaultMessage())))
                .collect(Collectors.toList());

        ex.printStackTrace();

        return ResponseEntity.ok(new ValidatedResponse<>(OperationStatus.FAILURE, errors));
    }
}
