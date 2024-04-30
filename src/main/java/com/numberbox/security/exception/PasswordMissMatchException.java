package com.numberbox.security.exception;

/**
 * 비밀번호 불일치
 */
public class PasswordMissMatchException extends RuntimeException {
    public PasswordMissMatchException() {
        super("비밀번호가 일치하지 않습니다.");
    }

    public PasswordMissMatchException(String msg) {
        super(msg);
    }
}
