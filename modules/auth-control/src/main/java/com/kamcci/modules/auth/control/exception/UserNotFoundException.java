package com.kamcci.modules.auth.control.exception;

import com.kamcci.modules.auth.control.exception.base.BaseAuthException;

import static com.kamcci.modules.auth.control.exception.code.Auth100ErrCodeType.USER_NOT_FOUND;

/**
 * 존재하지 않는 계정
 */
public class UserNotFoundException extends BaseAuthException {
    public UserNotFoundException() {
        super(USER_NOT_FOUND);
    }

}
