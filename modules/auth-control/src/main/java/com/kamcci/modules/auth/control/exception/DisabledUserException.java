package com.kamcci.modules.auth.control.exception;

import com.kamcci.modules.auth.control.exception.base.BaseAuthException;
import com.kamcci.modules.auth.control.exception.code.Auth100ErrCodeType;

/**
 * 비활성 계정
 */
public class DisabledUserException extends BaseAuthException {
    public DisabledUserException() {
        super(Auth100ErrCodeType.DISABLED_USER);
    }

}
