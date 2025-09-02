package com.exporum.client.configuration;

import com.exporum.client.filter.XssFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 23.
 * @description :
 */

@Configuration
public class FilterConfiguration {


    @Bean
    public FilterRegistrationBean<XssFilter> FilterRegistration() {
        FilterRegistrationBean<XssFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/api/v1/woc/*"); // url 패턴 설정
        //registration.addInitParameter("param1", "param_value1"); // 파라미터 설정
        registration.setName("xss-filter"); // 필터명 설정
        registration.setOrder(2); // 순서 설정
        return registration;
    }

}


