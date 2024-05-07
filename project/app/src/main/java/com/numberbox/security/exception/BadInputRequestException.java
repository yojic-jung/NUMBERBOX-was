package com.numberbox.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 잘못된 형식으로 인증 요청한 경우
 */
public class BadInputRequestException extends AuthenticationException {
    public BadInputRequestException() {
        super("잘못된 형식으로 요청함");
    }

    public BadInputRequestException(String msg) {
        super(msg);
    }
}
