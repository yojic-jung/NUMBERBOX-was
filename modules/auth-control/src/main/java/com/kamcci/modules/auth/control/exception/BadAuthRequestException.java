package com.kamcci.modules.auth.control.exception;

import com.kamcci.modules.auth.control.exception.base.BaseAuthException;
import com.kamcci.modules.auth.control.exception.code.BaseAuthErr1000CodeType;

/**
 * 클라이언트의 잘못된 형식 인증 요청
 */
public class BadAuthRequestException extends BaseAuthException {
    public BadAuthRequestException() {
        super(BaseAuthErr1000CodeType.BAD_AUTH_REQUEST_ERR_CODE);
    }
}
