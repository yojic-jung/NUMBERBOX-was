package com.numberbox.jwt.service;

import org.springframework.stereotype.Service;

import com.numberbox.jwt.entity.ExpiredRefreshToken;
import com.numberbox.jwt.repository.ExpiredRefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpiredRefreshTokenService {

    private final ExpiredRefreshTokenRepository expiredRefreshTokenRepository;

    public boolean isExpiredToken(String token) {
        return expiredRefreshTokenRepository.existsByToken(token);
    }

    public ExpiredRefreshToken addExpiredToken(String token) {
        ExpiredRefreshToken saveToken = ExpiredRefreshToken.builder()
            .token(token)
            .build();
        return expiredRefreshTokenRepository.save(saveToken);
    }
}
