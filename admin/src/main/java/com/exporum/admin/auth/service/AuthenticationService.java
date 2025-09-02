package com.exporum.admin.auth.service;

import com.exporum.admin.auth.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */
@Component
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        AuthUser authenticationUser = userDetailsService.customLoadUserByUserIdAndPassword(username, password);

        return new UsernamePasswordAuthenticationToken(authenticationUser, authenticationUser.getPassword(), authenticationUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
