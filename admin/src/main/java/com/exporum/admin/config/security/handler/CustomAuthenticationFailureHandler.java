package com.exporum.admin.config.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("onAuthenticationFailure : 로그인 실패 => {}", exception.getMessage());

        // 실패 원인 파악
        String errorReason;
        if (exception instanceof UsernameNotFoundException) {
            errorReason = "username_not_found";// 사용자 이름을 찾을 수 없는 경우
        } else if (exception instanceof BadCredentialsException) {
            errorReason = "bad_credentials";// 비밀번호가 일치하지 않는 경우
        } else if (exception instanceof LockedException) {
            errorReason = "account_locked";// 계정이 잠금 상태인 경우
        } else if (exception instanceof DisabledException) {
            errorReason = "account_disabled";// 계정이 비활성화(사용 불가) 상태인 경우
        } else if (exception instanceof AccountExpiredException) {
            errorReason = "account_expired";// 계정이 만료된 경우
        } else {
            errorReason = "unauthorized";// 위 경우에 해당하지 않는 기타 인증 실패
        }

        // 로그인 페이지로 리다이렉트 + error 파라미터 전달
        redirectStrategy.sendRedirect(request, response, "/login?error=" + errorReason);
    }

}
