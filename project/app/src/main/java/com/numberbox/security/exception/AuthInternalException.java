package com.numberbox.security.exception;


import org.springframework.security.core.AuthenticationException;

/**
 * 인증 과정 중 발생 예외
 */
public class AuthInternalException extends AuthenticationException {
    public AuthInternalException() {
        super("인증 예외");
    }

    public AuthInternalException(String msg) {
        super(msg);
    }
}
