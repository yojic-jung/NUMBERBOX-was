package com.numberbox.security.service;

import com.numberbox.security.dto.AuthUserDetail;
import com.numberbox.security.dto.AuthUserInfo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.UUID;

/**
 * Def. 모듈 사용자가 구현한 UserDetailsService 래퍼 객체
 */
public class JwtRequestUserDetailServiceWrapper implements UserDetailsService {
    private final JwtRequestUserDetailService jwtRequestUserDetailService;

    public JwtRequestUserDetailServiceWrapper(JwtRequestUserDetailService jwtRequestUserDetailService) {
        this.jwtRequestUserDetailService = jwtRequestUserDetailService;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserInfo authUserInfo = jwtRequestUserDetailService.loadUserByUsername(username);
        if(authUserInfo == null) return null;
        else return new AuthUserDetail(authUserInfo);
    }

    public boolean isTokenOwner(String token, UUID userId) {
        return jwtRequestUserDetailService.isTokenOwner(token, userId);
    }
}

