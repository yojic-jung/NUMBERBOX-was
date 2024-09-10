package com.kamcci.modules.auth.control.exception;

/**
 * 비활성 계정
 */
public class DisabledUserException extends RuntimeException {
    public DisabledUserException(){
        super("비활성 계정입니다.");
    }
    public DisabledUserException(String msg) {
        super(msg);
    }
}
