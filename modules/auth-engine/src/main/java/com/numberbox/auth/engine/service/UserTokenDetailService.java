package com.numberbox.auth.engine.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserTokenDetailService extends UserDetailsService {
    UUID loadUserIdByRefreshToken(String token);
}
