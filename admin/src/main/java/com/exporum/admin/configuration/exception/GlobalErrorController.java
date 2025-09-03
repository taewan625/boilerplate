package com.exporum.admin.configuration.exception;


import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
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
 * @since 2025. 9. 2. 최초 작성
 * @version 1.0
 * @modifier
 * @modified
 *
 */
@Controller
public class GlobalErrorController implements ErrorController {
    /**
     * 전역 에러 처리 메서드
     *
     * <p>
     * 1. Thymeleaf 파일 없음 예외 → 준비중 페이지
     * 2. 서버 오류(Exception 하위) → 5xx 에러 페이지
     * 3. 나머지 → 4xx 에러 페이지 또는 URL 직접 접근
     * </p>
     *
     * @param request HttpServletRequest - 에러 상태 코드와 예외 정보를 조회하기 위한 request 객체
     * @return String - 이동할 Thymeleaf 경로
     */
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);

        if (exception != null) {
            Class<?> exceptionType = exception.getClass();

            //Thymeleaf 파일 없음 예외 → 준비중 페이지
            if (exceptionType.equals(org.thymeleaf.exceptions.TemplateInputException.class)) {
                return "error/not-ready";
            }

            //모든 5xx 에러
            if (Exception.class.isAssignableFrom(exceptionType)) {
                return "error/runtime-error";
            }
        }

        //모든 4xx 에러 및 url 직접 접근
        return "error/not-found-error";
    }
}
