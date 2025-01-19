package com.kamcci.modules.auth.engine.util;

import java.util.List;
import java.util.UUID;

/**
 * 인증 토큰 유틸
 */
public interface AuthTokenUtil {
    // 액세스 토큰 생성
    String reCreateAccessToken(String email, UUID userUniqId, List<String> roleList);

    // 기존 액세스 토큰 정보로 재발급
    String reCreateAccessToken(String oldAccessToken);

    // 리프레시 토큰 생성
    String createRefreshToken(long validTime);

    // 리프레시 토큰 재발급
    String reCreateRefreshToken(String oldRefreshToken);

    // 액세스 토큰으로부터 email 추출
    String getEmail(String accessToken);

    // 액세스 토큰으로부터 userId 추출
    UUID getUserId(String accessToken);

    long getValidTime(String token);

    // 액세스 토큰으로부터 권한 추출
    List<String> getRoles(String accessToken);

    // 토큰 유효성 검사 - 유효하지 않은 경우 exception 발생
    void checkValidToken(String jwtToken, boolean checkExpire);

    // 토큰 만료여부 체크 - 유효한 토큰만 가능함
    boolean isExpiredToken(String jwtToken);
}
