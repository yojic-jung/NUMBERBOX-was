package com.numberbox.auth.engine.service;

import com.numberbox.auth.control.dto.AuthUserInfo;
import com.numberbox.auth.control.service.JwtRequestUserDetailService;
import com.numberbox.auth.control.service.LoginRequestUserDetailService;
import com.numberbox.auth.engine.dto.AuthUserDetail;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

/**
 * Def. 모듈 사용자가 구현한 UserDetailsService 래퍼 객체
 */
public class JwtRequestUserDetailServiceWrapper implements UserTokenDetailService {
    private final LoginRequestUserDetailService loginRequestUserService;
    private final JwtRequestUserDetailService jwtRequestUserDetailService;

    public JwtRequestUserDetailServiceWrapper(LoginRequestUserDetailService loginRequestUserService,
                                              JwtRequestUserDetailService jwtRequestUserDetailService) {
        this.loginRequestUserService = loginRequestUserService;
        this.jwtRequestUserDetailService = jwtRequestUserDetailService;
    }

    // todo 여기서 한번에 리프레시 토큰 까지 가져오기
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserInfo authUserInfo = loginRequestUserService.loadUserByUsername(username);
        if (authUserInfo == null) return null;
        else return new AuthUserDetail(authUserInfo);
    }

    @Override
    public UUID loadUserIdByRefreshToken(String token) {
        return jwtRequestUserDetailService.loadUserIdByRefreshToken(token);
    }
}

