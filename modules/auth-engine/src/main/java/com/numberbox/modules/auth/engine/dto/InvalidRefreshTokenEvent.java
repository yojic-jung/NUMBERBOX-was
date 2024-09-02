package com.numberbox.modules.auth.engine.dto;

/**
 * Def. 탈취, 만료, 무료한 리프레시 토큰인 경우 발행하는 이벤트
 */
public record InvalidRefreshTokenEvent(String remainedRefreshToken) {
}
