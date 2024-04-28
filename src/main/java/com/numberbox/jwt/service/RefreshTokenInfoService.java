package com.numberbox.jwt.service;

import com.numberbox.jwt.entity.RefreshTokenInfo;
import com.numberbox.jwt.repository.RefreshTokenInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

// todo
@Service
@RequiredArgsConstructor
public class RefreshTokenInfoService {

    @Autowired
    private RefreshTokenInfoRepository refreshTokenInfoRepository;

    public boolean isTokenMatched(String token, UUID userUniqId) {
        return refreshTokenInfoRepository.existsByTokenAndUserUniqId(token, userUniqId);
    }

    public void deleteByToken(String token) {
        refreshTokenInfoRepository.deleteByToken(token);
    }

    public void deleteByTokenCreateDateLessThan(int day) {
        refreshTokenInfoRepository.deleteByTokenCreateDateLessThan(LocalDateTime.now().minusDays(day));
    }

    public RefreshTokenInfo addRefreshToken(String token, UUID userUniqId) {
        RefreshTokenInfo saveToken = RefreshTokenInfo.builder().token(token).userUniqId(userUniqId).build();
        return refreshTokenInfoRepository.save(saveToken);
    }
}
