package com.exporum.admin.configuration.security.handler;

import com.exporum.admin.authentication.model.AuthUser;
import com.exporum.admin.common.model.ExhibitionSelectOption;
import com.exporum.admin.common.service.CommonService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${jwt.cookie-domain}")
    private String COOKIE_DOMAIN;

    private final CommonService commonService;
    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String remember = request.getParameter("remember");
        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        setCookie(response, remember, authUser.getUsername());

        request.getSession().setAttribute("role", authUser.getRoleName());
        request.getSession().setAttribute("adminName", authUser.getName());
        request.getSession().setAttribute("initialPassword", authUser.isInitialPassword());

        log.info("onAuthenticationSuccess :  로그인 성공");
        redirectStrategy.sendRedirect(request, response, "/dashboard");
    }


    private void setCookie(HttpServletResponse response, String remember, String username) {
        if(remember != null) {
            log.debug("Remember authentication success");

            Cookie cookie = new Cookie("username", username);
            cookie.setDomain(COOKIE_DOMAIN);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            response.addCookie(cookie);
        }else{
            Cookie cookie = new Cookie("username", "");
            cookie.setDomain(COOKIE_DOMAIN);
            response.addCookie(cookie);
        }

    }
}
