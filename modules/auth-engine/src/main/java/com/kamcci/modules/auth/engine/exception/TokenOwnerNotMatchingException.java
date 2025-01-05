package com.kamcci.modules.auth.engine.exception;

/**
 * Def. 토큰 소유자와 다른 경우
 */
public class TokenOwnerNotMatchingException extends TokenException {
    public TokenOwnerNotMatchingException() {
        super("액세스 토큰과 리프레시 토큰의 발급 대상이 다릅니다.");
    }

}