package com.exporum.admin.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author: Lee Hyunseung
 * @date : 2024. 12. 23.
 * @description :
 */


//@Component
@WebFilter
@Slf4j
public class XssFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("XssFilter doFilter");

        if (request instanceof HttpServletRequest) {

            HttpServletRequest httpRequest = (HttpServletRequest) request;

            chain.doFilter(new XssHttpservletRequestWrapper(httpRequest), response);

        } else {

            chain.doFilter(request, response);
        }
    }
}
