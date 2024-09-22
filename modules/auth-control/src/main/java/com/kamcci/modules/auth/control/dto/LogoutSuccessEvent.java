package com.kamcci.modules.auth.control.dto;

/**
 * Def. 로그아웃 성공 이벤트
 *
 * @param refreshToken - 로그아웃 요청한 리프레시 토큰
 */
public record LogoutSuccessEvent(String refreshToken) { }
