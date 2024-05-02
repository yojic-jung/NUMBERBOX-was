package com.numberbox.security.exception;

/**
 * Def. 리프레시 토큰 Null인 경우
 */
public class TokenNullException extends RuntimeException {
    public static final String DEFAULT_MSG = "액세스 토큰 또는 리프레시 토큰이 존재하지 않습니다.";

    public TokenNullException() {
        super(DEFAULT_MSG);
    }

    public TokenNullException(String msg) {
        super(msg);
    }
}