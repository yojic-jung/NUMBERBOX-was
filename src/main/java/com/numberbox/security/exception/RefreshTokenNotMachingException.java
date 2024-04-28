package com.numberbox.security.exception;

import lombok.Getter;

/**
 * Def. accessToken의 DB에 저장된 refreshToken의 주인이 다른 경우
 */
@Getter
public class RefreshTokenNotMachingException extends RuntimeException {
    public static final String DEFAULT_MSG = "액세스 토큰과 리프레시 토큰의 발급 대상이 다릅니다.";

    private final String refreshToken;

    public RefreshTokenNotMachingException(String refreshToken) {
        super(DEFAULT_MSG);
        this.refreshToken = refreshToken;
    }

    public RefreshTokenNotMachingException(String msg, String refreshToken) {
        super(msg);
        this.refreshToken = refreshToken;
    }
}