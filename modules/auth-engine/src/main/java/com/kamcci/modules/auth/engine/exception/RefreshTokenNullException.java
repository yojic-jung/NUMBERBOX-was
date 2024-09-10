package com.kamcci.modules.auth.engine.exception;

/**
 * Def. 리프레시 토큰 Null인 경우
 */
public class RefreshTokenNullException extends TokenException {
    public RefreshTokenNullException() {
        super("리프레시 토큰이 존재하지 않습니다.");
    }

    public RefreshTokenNullException(String msg) {
        super(msg);
    }
}