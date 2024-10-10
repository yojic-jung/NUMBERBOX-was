package com.kamcci.modules.auth.control.service;

import java.util.List;
import java.util.UUID;

/**
 * Def. 토큰을 response에 담음
 */
public interface TokenResponseService {
    // 액세스 토큰 재발급
    void refreshAccessToken(String oldAccessToken);

    // 액세스 토큰 및 리프레시 토큰을 만들어 response에 담음
    void responseAuthToken(String email, UUID userId, List<String> roleList);

    // accessToken, refreshToken을 response에 담음
    void setTokenToResponse(String accessToken, String refreshToken, List<String> roleList);
}
