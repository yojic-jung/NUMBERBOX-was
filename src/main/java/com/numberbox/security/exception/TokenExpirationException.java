package com.numberbox.security.exception;

import lombok.Getter;

/**
 * Def. Token 만료 예외
 */
@Getter
public class TokenExpirationException extends RuntimeException {
    public static final String DEFAULT_MSG = "리프레시 토큰이 만료되었습니다.";


    public TokenExpirationException() {
        super(DEFAULT_MSG);
    }

    public TokenExpirationException(String msg) {
        super(msg);
    }
}