package com.kamcci.modules.auth.control.exception.base;

import com.kamcci.modules.auth.control.exception.code.BaseAuthErrCodeType;

/**
 * Auth 모듈 기본 예외 타입
 */
public class BaseAuthException extends RuntimeException {
    final BaseAuthErrCodeType errCodeType;

    public BaseAuthException(BaseAuthErrCodeType errCodeType) {
        super("[KC-AUTH-" + errCodeType.getCode() + "]" + errCodeType.getMessage());
        this.errCodeType = errCodeType;
    }
}
