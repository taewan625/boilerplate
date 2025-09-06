package com.exporum.admin.config;

import com.exporum.admin.config.interceptor.RefererLoggingInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Kwon Taewan
 * @date : 2025. 07. 18.
 * @description : 웹 관련 설정을 중앙에서 관리하는 클래스.
 *              모든 Interceptor 등록, Formatter 등 관리
 */
//@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RefererLoggingInterceptor refererLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Referer 로깅 인터셉터를 등록합니다.
        registry.addInterceptor(refererLoggingInterceptor)
                .addPathPatterns("/**") // 모든 경로에 적용하고
                .excludePathPatterns("/api/**") // /api/** 경로는 제외합니다.
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**"); // 정적 리소스 경로도 제외합니다.
    }
}