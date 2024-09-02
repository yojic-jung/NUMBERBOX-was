package com.numberbox.modules.auth.control.exception;

/**
 * 존재하지 않는 계정
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("해당 계정이 없습니다.");
    }
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
