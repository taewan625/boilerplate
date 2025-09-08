package com.exporum.admin.config.exception;

import com.exporum.core.enums.OperationStatus;
import com.exporum.core.exception.DataNotFoundException;
import com.exporum.core.exception.OperationFailException;
import com.exporum.core.exception.ThirdPartyApiException;
import com.exporum.core.response.OperationResponse;
import com.exporum.core.response.ValidatedResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    /**
     * view
     * 404 Not Found 페이지 처리
     * 브라우저에서 잘못된 URL 요청 시 호출
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException() {
        return "errors/not-found-error";
    }

    /**
     * view
     * 일반 런타임 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "errors/runtime-error";
    }

    /**
     * rest api
     * 서드파티 API 호출 실패 예외
     */
    @ExceptionHandler(ThirdPartyApiException.class)
    public ResponseEntity<OperationResponse> handleThirdPartyApiException(ThirdPartyApiException ex) {
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * rest api
     * 비즈니스 로직 수행 실패 예외
     */
    @ExceptionHandler(OperationFailException.class)
    public ResponseEntity<OperationResponse> handleOperationFailException(OperationFailException ex) {
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    /**
     * rest api
     * 데이터 조회 실패 (없는 데이터) 예외
     */
    @ExceptionHandler({DataNotFoundException.class, NoResourceFoundException.class})
    public ResponseEntity<OperationResponse> handleDataNotFoundException(RuntimeException ex) {
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * rest api
     * 요청 파라미터 검증 실패
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidatedResponse<List<Map<String, String>>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> Map.of(error.getField(), Objects.requireNonNull(error.getDefaultMessage())))
                .collect(Collectors.toList());

        return new ResponseEntity<>(new ValidatedResponse<>(OperationStatus.FAILURE, errors), HttpStatus.BAD_REQUEST);
    }

    /**
     * rest api
     * 필수 요청 파라미터 누락
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<OperationResponse> handleRequestParameterException(MissingServletRequestParameterException ex) {
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * rest api
     * SQL 문법 오류 (DB 관련)
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<OperationResponse> handleBadSqlGrammar(BadSqlGrammarException ex) {
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage("SQL 문법 오류가 발생했습니다.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * rest api
     * 범용 REST API 예외 처리 (모든 예외)
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<OperationResponse> handleGlobalRestException(Exception ex) {
        ex.printStackTrace(); // 로그 출력
        OperationResponse response = new OperationResponse(OperationStatus.FAILURE);
        response.setMessage("알 수 없는 오류가 발생했습니다.");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
