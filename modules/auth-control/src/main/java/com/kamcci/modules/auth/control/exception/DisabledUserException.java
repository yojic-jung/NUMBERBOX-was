package com.kamcci.modules.auth.control.exception;

/**
 * 비활성 계정
 */
public class DisabledUserException extends RuntimeException {
    public DisabledUserException() {
        super("과도한 비밀번호 실패로 인해 계정이 비활성화 되었습니다.\n15분 후 다시 시도 해주세요");
    }

    public DisabledUserException(String msg) {
        super(msg);
    }
}
