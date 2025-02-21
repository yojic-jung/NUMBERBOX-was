package com.kamcci.modules.auth.mock.service;

import com.kamcci.modules.auth.control.service.TokenResponseService;

import java.util.List;
import java.util.UUID;

public class MockTokenResponseService implements TokenResponseService {
    // 실행 여부
    public int executeCnt = 0;

    // 액세스 토큰 및 리프레시 토큰 재발급하여 응답 - oldRefreshToken != null 경우만 리프레시 토큰 재발급
    @Override
    public void responseAuthToken(String oldAccessToken, String oldRefreshToken) {
        executeCnt++;
    }

    // 액세스 토큰 및 리프레시 토큰을 만들어 response에 담음
    @Override
    public void responseAuthToken(String email, UUID userId, List<String> roleList) {
        executeCnt++;
    }

    // 액세스 토큰 및 리프레시 토큰을 response에 담음
    @Override
    public void setTokenToResponse(String accessToken, String refreshToken, List<String> roleList) {
        executeCnt++;
    }
}
