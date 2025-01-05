package com.kamcci.modules.auth.engine.exception;

/**
 * Def. 유효하지 않은 토큰 예외
 * - access-token 만기는 예외에 포함되지 않음(access-toke 만기시 refresh-token이 재발급함으로)
 */
public class JwtInvalidException extends TokenException {
    public JwtInvalidException() {
        super("액세스 토큰 또는 리프레시 토큰이 유효하지 않습니다.");
    }

}