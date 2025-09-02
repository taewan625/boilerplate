package com.exporum.client.configuration.security;

import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 11. 19.
 * @description :
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 검사 제외 경로 추가
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher("/api/v1/woc/payment/webhook") //결제 webhook 경로
                )
        );

        http.cors(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(request -> request
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                //경로 차단
                .requestMatchers(
                        new AntPathRequestMatcher("/WEB-INF/**"),
                        //new AntPathRequestMatcher("/error/**"),
                        new AntPathRequestMatcher("/.well-known/**")
                ).denyAll()
                //나머지 모든 경로는 허용
                .anyRequest().permitAll()
        );

        return http.build();
    }

}
