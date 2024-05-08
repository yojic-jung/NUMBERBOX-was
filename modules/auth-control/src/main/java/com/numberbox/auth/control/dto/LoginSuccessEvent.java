package com.numberbox.auth.control.dto;

import java.util.UUID;

/**
 * Def. 로그인 성공 이벤트
 * 
 * @param userId - 사용자 id
 * @param refreshToken - 발행한 리프레시 토큰
 * @param remainedRefreshToken - nullable, 로그인 시도시 존재하고 있는 리프레시 토큰(삭제대상)
 */
public record LoginSuccessEvent(UUID userId, String refreshToken, String remainedRefreshToken) {
}
