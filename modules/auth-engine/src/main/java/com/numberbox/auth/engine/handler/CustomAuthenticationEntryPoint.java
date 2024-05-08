package com.numberbox.auth.engine.handler;

import com.numberbox.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import static com.numberbox.auth.control.dto.AuthResponse.ACCESS_DENIED;

// todo 무슨 역할인지 확인 필요
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) {
        AuthWebUtil.responseErrMsg(response, ACCESS_DENIED.statusCode, ACCESS_DENIED.message);
    }
}
