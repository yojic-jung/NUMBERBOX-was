package com.numberbox.security.exception;

import lombok.Getter;

/**
 * Def. Token 만료 예외
 */
@Getter
public class TokenExpirationException extends TokenException {
    public TokenExpirationException() {
        super("리프레시 토큰이 만료되었습니다.");
    }

    public TokenExpirationException(String msg) {
        super(msg);
    }
}