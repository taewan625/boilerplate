package com.exporum.client.interceptor;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: Kwon Taewan
 * @date : 2025. 07. 18.
 * @description :
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RefererLoggingInterceptor refererLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //referer logging 인터셉터 적용 범위 설정: 전체 경로에 대해 적용하되 api 통신, 정적 리소스 제외
        registry.addInterceptor(refererLoggingInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**");
    }
}
