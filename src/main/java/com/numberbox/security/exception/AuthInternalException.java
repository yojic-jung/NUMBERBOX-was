package com.numberbox.security.exception;

/**
 * 인증 과정 중 발생 예외
 */
public class AuthInternalException extends RuntimeException {
    public AuthInternalException() {
    }

    public AuthInternalException(String msg) {
        super(msg);
    }
}
