package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.kamcci.modules.auth.control.dto.AuthResponse.ACCESS_DENIED;

/**
 * 인증되지 않은 사용자가 리소스 접근시 호출됨
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        AuthWebUtil.responseErrMsg(response, ACCESS_DENIED.statusCode, ACCESS_DENIED.message);
    }
}
