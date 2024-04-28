package com.numberbox.security.exception;

import lombok.Getter;

/**
 * Def. refreshToken 만료 예외
 */
@Getter
public class RefreshTokenExpirationException extends RuntimeException {
    public static final String DEFAULT_MSG = "리프레시 토큰이 만료되었습니다.";

    private final String refreshToken;

    public RefreshTokenExpirationException(String refreshToken) {
        super(DEFAULT_MSG);
        this.refreshToken = refreshToken;
    }

    public RefreshTokenExpirationException(String msg, String refreshToken) {
        super(msg);
        this.refreshToken = refreshToken;
    }
}