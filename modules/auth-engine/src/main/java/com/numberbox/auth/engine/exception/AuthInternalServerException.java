package com.numberbox.auth.engine.exception;

/**
 * 인증 과정 중 발생 예외
 */
public class AuthInternalServerException extends RuntimeException {
    public AuthInternalServerException() {
        super("인증 과정 중 예외 발생");
    }

    public AuthInternalServerException(String msg) {
        super(msg);
    }
}
