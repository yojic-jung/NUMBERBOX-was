package com.kamcci.modules.auth.engine.exception;

/**
 * Def. Token 만료 예외
 */
public class TokenExpirationException extends TokenException {
    public TokenExpirationException() {
        super("리프레시 토큰이 만료되었습니다.");
    }

    public TokenExpirationException(String msg) {
        super(msg);
    }
}