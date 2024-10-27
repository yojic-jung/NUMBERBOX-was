package com.kamcci.modules.auth.control.exception.base;

import com.kamcci.modules.auth.control.exception.code.BaseAuthErrCodeType;

/**
 * Auth 모듈 기본 예외 타입
 */
public class BaseAuthException extends RuntimeException {
    public BaseAuthException(BaseAuthErrCodeType errCodeType) {
        super("[KC-AUTH-ERR-" + errCodeType.getCode() + "]" + errCodeType.getMessage());
    }

    public BaseAuthException(String msg) {
        super(msg);
    }
}
