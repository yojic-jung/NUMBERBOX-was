package com.numberbox.security.exception;

/**
 * 비밀번호 불일치
 */
public class PasswordDisMatchException extends RuntimeException {
    public PasswordDisMatchException(String msg) {
        super(msg);
    }
}
