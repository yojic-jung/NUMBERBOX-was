package com.numberbox.security.exception;

/**
 * 비활성 계정
 */
public class DisabledUserException extends RuntimeException {
    public DisabledUserException(String msg) {
        super(msg);
    }
}
