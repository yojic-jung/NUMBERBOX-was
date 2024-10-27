package com.kamcci.modules.auth.control.exception;

import com.kamcci.modules.auth.control.exception.base.BaseAuthException;
import com.kamcci.modules.auth.control.exception.code.Auth100ErrCodeType;

/**
 * 클라이언트의 잘못된 형식 인증 요청
 */
public class BadAuthRequestException extends BaseAuthException {
    public BadAuthRequestException() {
        super(Auth100ErrCodeType.BAD_AUTH_REQUEST);
    }
}
