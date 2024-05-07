package com.numberbox.auth.engine.service;

import com.numberbox.auth.control.service.LoginRequestUserDetailService;
import com.numberbox.auth.engine.dto.AuthUserDetail;
import com.numberbox.auth.control.dto.AuthUserInfo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Def. 모듈 사용자가 구현한 UserDetailsService 래퍼 객체
 */
public class LoginRequestUserDetailServiceWrapper implements UserDetailsService {
    private final LoginRequestUserDetailService loginRequestUserService;

    public LoginRequestUserDetailServiceWrapper(LoginRequestUserDetailService loginRequestUserService) {
        this.loginRequestUserService = loginRequestUserService;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserInfo authUserInfo = loginRequestUserService.loadUserByUsername(username);
        if(authUserInfo == null) return null;
        else return new AuthUserDetail(authUserInfo);
    }
}

