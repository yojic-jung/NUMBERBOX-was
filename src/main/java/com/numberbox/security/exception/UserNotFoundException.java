package com.numberbox.security.exception;

/**
 * 존재하지 않는 계정
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
