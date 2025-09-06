package com.exporum.admin.domain.authentication.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author: Lee Hyunseung
 * @date : 2025. 1. 20.
 * @description :
 */


@Getter
@Setter
public class AuthUser implements UserDetails {
    private String id;
    private String role;
    private String roleName;
    private String department;
    private String email;
    private String password;
    private String name;
    private String mobileNumber;
    private boolean initialPassword;
    private boolean expiration;
    private boolean blocked;
    private String lastLoginAt;



    /**
     * 계정이 가지고 있는 권한 목록
     * @return null
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //Arrays.asList(new SimpleGrantedAuthority("ROLE_"+this.getUserType().getCode()))
        //List.of(new SimpleGrantedAuthority("ROLE_" + this.getUserType().getCode()));
        return List.of(new SimpleGrantedAuthority(role));
    }

    /**
     * 사용자 아이디
     * @return userId
     */
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    /**
     * 계정 만료 여부 (false: 만료, true: 만료 안됨)
     * @return boolean
     */
    @Override
    public boolean isAccountNonExpired() {
        return !this.blocked;
    }

    /**
     * 계정 잠김 여부 (false: 만료, true: 만료 안됨)
     * @return boolean
     */
    @Override
    public boolean isAccountNonLocked() {
        return !this.blocked;
    }

    /**
     * 비밀번호 만료 여부 (false: 만료, true: 만료 안됨)
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return !this.expiration;
    }

    /**
     * 계정 활성화 여부 (false: 비활성, true: 활성)
     * @return
     */
    @Override
    public boolean isEnabled() {
        return !this.blocked;
    }
}
