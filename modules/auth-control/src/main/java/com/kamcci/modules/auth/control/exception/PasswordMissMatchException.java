package com.kamcci.modules.auth.control.exception;

import com.kamcci.modules.auth.control.exception.base.BaseAuthException;
import com.kamcci.modules.auth.control.exception.code.Auth100ErrCodeType;

/**
 * 비밀번호 불일치
 */
public class PasswordMissMatchException extends BaseAuthException {
    public PasswordMissMatchException() {
        super(Auth100ErrCodeType.PASSWORD_MISS_MATCH);
    }

}
