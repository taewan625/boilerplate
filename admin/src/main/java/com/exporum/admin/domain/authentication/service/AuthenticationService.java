package com.exporum.admin.domain.authentication.service;


import com.exporum.admin.domain.authentication.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * AuthenticationService.java
 *
 * <p>
 * description
 * Spring Security에서 사용자 인증(Authentication)을 처리하는 커스텀 AuthenticationProvider 구현체
 * id, pw 기반, 인증 성공 시 Authentication 객체를 반환합니다.
 * </p>
 *
 * @author Kwon Taewan
 * @version 1.0
 * @modifier
 * @modified
 * @since 2025. 9. 3. 최초 작성
 */
@Component
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;

    //Spring Security 내부에서 SecurityContext에 저장되어, 이후 요청에서 로그인 상태로 인정
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AuthUser user = userDetailsService.authenticateLoginInfo(username, password);

        //인증 성공 token 객체 SecurityContext에 저장
        //todo. 3rd params의 권한 부분은 파악 후 추가 여부 판단
        return new UsernamePasswordAuthenticationToken(user, user.getPassword(),  List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }

    //provider가 처리할 수 있는 Authentication 타입 지정
    @Override
    public boolean supports(Class<?> authentication) {
        //spring security가 제공하는 인증 객체
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}