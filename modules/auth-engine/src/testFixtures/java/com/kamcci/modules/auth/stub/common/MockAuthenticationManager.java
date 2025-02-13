package com.kamcci.modules.auth.stub.common;

import com.kamcci.modules.auth.engine.exception.AuthInternalException;
import com.kamcci.modules.auth.engine.exception.TokenException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.STUB_EXCEPTION_MSG;

public class MockAuthenticationManager implements AuthenticationManager {
    public static final int TOKEN_EXCEPTION_TYPE = 1;
    public static final int DISABLE_EXCEPTION_TYPE = 2;
    public static final int AUTH_EXCEPTION_TYPE = 3;
    public static final int RUNTIME_EXCEPTION_TYPE = 4;
    // 예외 터트릴 타입
    public int exceptionType = 0;
    // 실행 여부
    public int executeCnt = 0;

    @Override
    public Authentication authenticate(Authentication authentication) throws RuntimeException {
        if(exceptionType == TOKEN_EXCEPTION_TYPE) throw new TokenException(STUB_EXCEPTION_MSG);
        if(exceptionType == DISABLE_EXCEPTION_TYPE) throw new DisabledException(STUB_EXCEPTION_MSG);
        if(exceptionType == AUTH_EXCEPTION_TYPE) throw new AuthInternalException(STUB_EXCEPTION_MSG);
        if(exceptionType == RUNTIME_EXCEPTION_TYPE) throw new RuntimeException(STUB_EXCEPTION_MSG);
        executeCnt++;
        return authentication;
    }
}
