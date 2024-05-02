package com.numberbox.security.exception;

import lombok.Getter;

/**
 * Def. 토큰 소유자와 다른 경우
 */
@Getter
public class TokenOwnerNotMatchingException extends RuntimeException {
    public static final String DEFAULT_MSG = "액세스 토큰과 리프레시 토큰의 발급 대상이 다릅니다.";

    private final String refreshToken;

    public TokenOwnerNotMatchingException(String refreshToken) {
        super(DEFAULT_MSG);
        this.refreshToken = refreshToken;
    }

    public TokenOwnerNotMatchingException(String msg, String refreshToken) {
        super(msg);
        this.refreshToken = refreshToken;
    }
}