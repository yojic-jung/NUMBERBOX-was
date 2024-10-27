package com.kamcci.modules.auth.control.dto;

/**
 * 로그인 인증 처리 메시지
 */
public enum AuthResponse {
    LOGIN_OK(200, "로그인에 성공하였습니다."),  // 로그인 성공
    LOGOUT_OK(200, "로그아웃에 성공하였습니다."),  // 로그아웃 성공
    ACCESS_DENIED(401, "해당 요청에 접근 권한이 없습니다."); // 접근 권한 없음
    public final int statusCode;
    public final String message;

    AuthResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
