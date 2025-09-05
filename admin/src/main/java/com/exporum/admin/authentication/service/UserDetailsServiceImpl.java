package com.exporum.admin.authentication.service;

import com.exporum.admin.authentication.mapper.AuthMapper;
import com.exporum.admin.authentication.model.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 2. 7.
 * @description :
 */


@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public AuthUser authenticateLoginInfo(String id, String password) throws UsernameNotFoundException {
        AuthUser user = Optional.ofNullable(authMapper.getUser(id)).orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        throw new BadCredentialsException("비밀번호가 틀렸습니다.");
    }

}
