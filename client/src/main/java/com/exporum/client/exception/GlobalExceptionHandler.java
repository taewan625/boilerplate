package com.exporum.client.exception;

import com.exporum.core.enums.OperationStatus;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.exception.ThirdPartyApiException;
import com.exporum.core.response.OperationResponse;
import com.exporum.core.response.ValidatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 20.
 * @description :
 * @modified 2025-06-26 권태완 - RestController에서 동작하도록 분기 처리
 */

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return "error/not-found-error";
    }

    @ExceptionHandler(FileNotFoundException.class)
    public String handleTemplateInputException(TemplateInputException ex) {
        ex.printStackTrace();
        return "error/not-ready";
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex) {
        ex.printStackTrace();
        return "error/runtime-error";
    }


    @ExceptionHandler(ThirdPartyApiException.class)
    public ResponseEntity<OperationResponse> handleThirdPartyApiException(ThirdPartyApiException ex) {

        OperationResponse response =new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(OperationFailException.class)
    public ResponseEntity<OperationResponse> handleOperationFailException(OperationFailException ex) {

        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<OperationResponse> handleDataNotFoundException(DataNotFoundException ex) {

        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(VerifyEmailException.class)
    public ResponseEntity<OperationResponse> handleVerifyEmailException(VerifyEmailException ex) {

        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }


    //404 ERROR
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<OperationResponse> handleNoResourceFoundException(NoResourceFoundException ex) {

        OperationResponse response =new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidatedResponse<List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex) {

        ex.printStackTrace();
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(error.getField(), Objects.requireNonNull(error.getDefaultMessage())))
                .collect(Collectors.toList());


        return new ResponseEntity<>(new ValidatedResponse<>(OperationStatus.FAILURE, errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<OperationResponse> handleRequestParameterException(MissingServletRequestParameterException ex) {

        OperationResponse response =new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        ex.printStackTrace();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
