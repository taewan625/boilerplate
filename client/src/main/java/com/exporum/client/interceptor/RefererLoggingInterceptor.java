package com.exporum.client.interceptor;

import com.exporum.core.domain.referer.model.ConnectionLog;
import com.exporum.core.domain.referer.service.RefererService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

/**
 * @author: Kwon Taewan
 * @date : 2025. 07. 18.
 * @description :
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RefererLoggingInterceptor implements HandlerInterceptor {

    private final RefererService refererService;

    @Value("${app.referer.exclude-domain}")
    private String excludeDomain;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String referer = request.getHeader("Referer");

        String userAgent = request.getHeader("User-Agent");

        String ip = request.getHeader("X-Forwarded-For");//하나 이상의 프록시 서버 또는 로드 밸런서가 있을 경우 원본 client 주소 조회
        if (ip == null || ip.isBlank() || "unknown".equalsIgnoreCase(ip)){
            ip = request.getRemoteAddr(); //웹 서버와 직접 TCP/IP 연결을 맺은 client IP 주소
        }

        String url = request.getRequestURL().toString();

        String parameter = request.getQueryString(); //GET 요청만 고려

        //String language = request.getHeader("Accept-Language");
        Locale language = request.getLocale() == null ? Locale.getDefault() : request.getLocale();

        if (referer != null && !referer.isBlank() && !referer.startsWith(excludeDomain)) {
            log.info("Referer Logging - IP: {}, Referer: {}, User-Agent: {}, url: {}, parameter: {}, language: {}", ip, referer, userAgent, url, parameter, language);
            refererService.insertReferer(ConnectionLog.builder()
                    .referer(referer)
                    .userAgent(userAgent)
                    .ip(ip)
                    .url(url)
                    .parameter(parameter)
                    .language(language.toString())
                    .build());
        } else {
            log.info("No valid referer - IP: {}, Referer: {}, User-Agent: {}, url: {}, parameter: {}, language: {}", ip, referer, userAgent, url, parameter, language);
        }

        return true;
    }
}
