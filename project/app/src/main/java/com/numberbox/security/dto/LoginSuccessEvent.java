package com.numberbox.security.dto;

import jakarta.annotation.Nullable;

import java.util.UUID;

/**
 * Def. 로그인 성공 이벤트
 */
public record LoginSuccessEvent(UUID userId, String refreshToken, @Nullable String remainedRefreshToken) {
}
