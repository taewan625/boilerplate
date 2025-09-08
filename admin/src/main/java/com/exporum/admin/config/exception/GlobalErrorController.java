package com.exporum.admin.config.exception;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * GlobalErrorController.java
 *
 * <p>
 * description
 * ControllerAdvice(=mvc 영역 내에서 예외처리)에서 처리하지 못하는 범주의 예외를 처리하는 클래스
 * template Engine을 사용하는 프로젝트의 경우 필수 세팅
 * 준비중 page, mvc에서 잡지 못한 4xx, 5xx 에러 페이지 처리
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 2. 최초 작성
 */
@Controller
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object statusCodeObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exceptionObj = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        Throwable cause = null;
        if (exceptionObj instanceof Throwable t) {
            cause = t;
            // 래핑된 예외 확인
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
        }

        // 최종 cause가 TemplateInputException인지 확인
        if (cause instanceof org.thymeleaf.exceptions.TemplateInputException) {
            return "errors/not-ready"; // 템플릿 관련 에러 페이지
        }

        // status code 기반 처리
        if (statusCodeObj != null) {
            int status = Integer.parseInt(statusCodeObj.toString());
            if (status >= 500) return "errors/runtime-error";
            if (status == 404) return "errors/not-found-error";
            if (status >= 400) return "errors/bad-request-error";
        }

        return "errors/runtime-error";
    }
}
