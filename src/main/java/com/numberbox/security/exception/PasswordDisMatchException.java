package com.numberbox.security.exception;

/**
 * 비밀번호 불일치
 */
public class PasswordDisMatchException extends RuntimeException {
    public PasswordDisMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }

    public PasswordDisMatchException(String msg) {
        super(msg);
    }
}
