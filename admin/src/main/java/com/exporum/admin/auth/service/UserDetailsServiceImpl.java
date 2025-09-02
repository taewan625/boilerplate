package com.exporum.admin.auth.service;

import com.exporum.admin.auth.mapper.AuthMapper;
import com.exporum.admin.auth.model.AuthUser;
import com.exporum.admin.auth.model.ChangePassword;
import com.exporum.admin.helper.AuthenticationHelper;
import com.exporum.core.exception.OperationFailException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public AuthUser customLoadUserByUserIdAndPassword(String username, String password) throws UsernameNotFoundException{
      AuthUser user = Optional.ofNullable(authMapper.getUser(username)).orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));

        if(passwordEncoder.matches(password, user.getPassword())){
            authMapper.updateLastLoginTime(username);
            return user;
        }
        throw new BadCredentialsException("비밀번호가 틀렸습니다.");

    }


    @Transactional
    public void changePassword(ChangePassword changePassword, HttpServletRequest request) {
        long id = AuthenticationHelper.getAuthenticationUserId();
        AuthUser user = Optional.ofNullable(authMapper.getChangePassword(id)).orElseThrow(()->new OperationFailException("비밀번호 변경에 실패하였습니다. 다시 시도해주세요."));

        if(passwordEncoder.matches(changePassword.getPassword(), user.getPassword())){
            changePassword.setNewPassword(passwordEncoder.encode(changePassword.getNewPassword()));

            if(!(authMapper.changePassword(id,changePassword)>0)){
                throw new OperationFailException("비밀번호 변경에 실패하였습니다. 다시 시도해주세요.");
            }

            request.getSession().setAttribute("initialPassword", false);
        }else{
            throw new OperationFailException("현재 비밀번호를 확인해주세요.");
        }

    }

}
