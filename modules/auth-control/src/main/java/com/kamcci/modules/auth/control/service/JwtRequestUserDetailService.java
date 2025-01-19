package com.kamcci.modules.auth.control.service;

import java.util.UUID;

/**
 * Def. 서버측 사용자 인증 정보 제공 인터페이스
 * <p>
 * - 호출 모듈에서 해당 인터페이스를 구현하여 인증 정보 반환해야함
 */
public interface JwtRequestUserDetailService {
    // 사용자 인증정보 반환
    UUID loadUserIdByRefreshToken(String token);

    // 사용자 리프레시 토큰 재발급 가능 여부 결정
    boolean canReCreateRefreshToken(UUID userId);
}
