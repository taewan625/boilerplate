package com.exporum.admin.configuration.security;

import com.exporum.admin.configuration.security.handler.CustomAuthenticationFailureHandler;
import com.exporum.admin.configuration.security.handler.CustomAuthenticationSuccessHandler;
import jakarta.servlet.DispatcherType;
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
 * @author: Lee Hyunseung
 * @date : 2024. 11. 19.
 * @description :
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
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/static/**")).permitAll()
                .requestMatchers(new AntPathRequestMatcher("/login/**")).permitAll()
                .requestMatchers(
                        new AntPathRequestMatcher("/api-doc/**"),
                        new AntPathRequestMatcher("/swagger-ui.html"),
                        new AntPathRequestMatcher("/v3/**"),
                        new AntPathRequestMatcher("/swagger-resources/**"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/webjars/**")
                ).permitAll()

                //todo 2.deny 유형

                //3.이외 모든 요청 인증
                .anyRequest().authenticated()
        );

        //인증 방법 - form 로그인
        //실제 인증 flow: AuthenticationService → UserDetailServiceImpl
        http.formLogin(login -> login
                .loginPage("/login")         //login form url
                .loginProcessingUrl("/login")  //login 요청 처리 url
                .usernameParameter("username") //id
                .passwordParameter("password") //pw
                .successHandler(customAuthenticationSuccessHandler) //실제 인증 작업 성공 후처리
                .failureHandler(customAuthenticationFailureHandler) //실제 인증 작업 실패 후처리
                .permitAll()
        );

        //logout 위임 (session 무효화, securityContext 초기화, 쿠키 제거)
        http.logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login") //logout후 이동할 url
        );

        return http.build();
    }
}
