package com.exporum.admin.config.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 20.
 * @description :
 * @modified 2025-06-26 권태완 - RestController에서 동작하도록 분기 처리
 */

@ControllerAdvice(annotations = Controller.class)
public class GlobalViewExceptionHandler {
    /**
     * 404 Not Found 페이지 처리
     * 브라우저에서 잘못된 URL 요청 시 호출
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNoHandlerFoundException() {
        return "errors/not-found-error";
    }

    /**
     * 일반 런타임 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public String handleException() {
        return "errors/runtime-error";
    }
}
