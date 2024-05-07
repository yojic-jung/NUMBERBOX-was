package com.numberbox.security.dto;

import org.springframework.http.HttpStatus;

/**
 * 로그인 인증 처리 메시지
 */
public enum AuthResponse {
    OK(HttpStatus.OK, "로그인에 성공하였습니다."),
    BAD_AUTH_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 형식의 요청입니다."),
    USER_NOT_FOUND(HttpStatus.FORBIDDEN, "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다."),
    AUTH_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "로그인 인증 과정 중 서버 예외 발생"),
    ABLE_USER(HttpStatus.FORBIDDEN, "계정 잠금이 풀렸습니다.\n다시 로그인 시도해주세요."),
    DISABLE_USER(HttpStatus.FORBIDDEN, "해당 계정이 잠금되었습니다.\n15분 후 다시 시도해주세요."),
    PASSWORD_MISS_MATCH(HttpStatus.FORBIDDEN, "이메일과 비밀번호를 다시 한번 입력해주시기 바랍니다.\n" +
            "5회 이상 실패시 15분간 계정이 비활성화 됩니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 요청에 접근 권한이 없습니다.");

    public final HttpStatus status;
    public final String message;

    AuthResponse(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }
}
