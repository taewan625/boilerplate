package com.exporum.admin.config.security;

import com.exporum.admin.config.security.handler.CustomAuthenticationFailureHandler;
import com.exporum.admin.config.security.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

/**
 * SecurityConfiguration.java
 *
 * <p>
 * description
 *
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @since 2025. 9. 7. 최초 작성
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    //비밀번호 암호화 강도 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        http.csrf(CsrfConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);

        //접근 유무 설정
        http.authorizeHttpRequests(request -> request
                //1.permit 유형
                .requestMatchers("/static/**", "/login/**").permitAll()
                .requestMatchers(
                        "/api-doc/**",
                        "/swagger-ui.html",
                        "/v3/**",
                        "/swagger-resources/**",
                        "/swagger-ui/**",
                        "/webjars/**"
                ).permitAll()

                //todo 2.deny 유형

                //3.이외 모든 요청 인증
                .anyRequest().authenticated()
        );

        //계정 접속 설정
        http.sessionManagement(session -> session
                        .maximumSessions(1) // 한 계정 당 최대 허용 세션 수 todo properties로 설정
                        .maxSessionsPreventsLogin(true) //true: 기존 세션 유지, 새 로그인 차단 //false: 기존 세션 만료, 새 로그인 허용
        );

        /*
         * 인증 방법: Form Login
         * 인증 과정: AuthenticationProvider → UserDetailsService
         *
         * 기본값:
         * - 로그인 처리 URL: POST /login
         * - 사용자 이름 파라미터: username
         * - 비밀번호 파라미터: password
         * - 로그인 페이지: /login (커스터마이징 시 필요)
         *
         * successHandler, failureHandler 등 커스터마이징 필요 없으면 생략 가능
         * */
        http.formLogin(login -> login
                .loginPage("/login")            //login form url
                .successHandler(customAuthenticationSuccessHandler) //실제 인증 작업 성공 후처리
                .failureHandler(customAuthenticationFailureHandler) //실제 인증 작업 실패 후처리
                .permitAll()
        );

        /*
         * http.logout(logout -> {});
         *
         * Spring Security 6 내장 기능
         * - 기본 logout 기능은 내장되어 있어 별도 설정 없이도 동작
         * - 기본 동작:
         *     • URL: POST /logout
         *     • 로그아웃 후 리다이렉트: /login?logout
         *     • 세션 무효화
         *     • SecurityContext 초기화
         *     • 관련 쿠키 제거
         *
         * 별도 커스터마이징이 필요 없다면 http.logout() 호출 자체도 생략 가능
         * */

        return http.build();
    }
}
