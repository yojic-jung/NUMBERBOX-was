package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.control.exception.BadAuthRequestException;
import com.kamcci.modules.auth.control.exception.DisabledUserException;
import com.kamcci.modules.auth.control.exception.PasswordMissMatchException;
import com.kamcci.modules.auth.control.exception.UserNotFoundException;
import com.kamcci.modules.auth.engine.config.AuthLoginUrlProperty;
import com.kamcci.modules.auth.engine.exception.AuthInternalServerException;
import com.kamcci.modules.auth.engine.exception.BadInputRequestException;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthLoginUrlProperty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LoginFailureHandlerTest {
    private final AuthLoginUrlProperty authLoginUrlProperty = getAuthLoginUrlProperty();
    private final LoginFailureHandler loginFailureHandler = new LoginFailureHandler(authLoginUrlProperty);

    @Test
    void 인증실패_api_호출_성공() throws ServletException, IOException {
        // given
        // g1. 발생 예외에 따른 예외 전환 클래스 타입
        Map<AuthenticationException, Class<? extends Exception>> exMap = new HashMap<>();
        exMap.put(new UsernameNotFoundException(""), UserNotFoundException.class);
        exMap.put(new BadCredentialsException(""), PasswordMissMatchException.class);
        exMap.put(new DisabledException(""), DisabledUserException.class);
        exMap.put(new BadInputRequestException(""), BadAuthRequestException.class);
        exMap.put(new AccountExpiredException(""), AuthInternalServerException.class);
        // request 및 response 설정
        MockHttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();
        request.setDispatcherType(DispatcherType.REQUEST);

        for(AuthenticationException ex : exMap.keySet()) {
            // when
            loginFailureHandler.onAuthenticationFailure(request, response, ex);

            // then
            assertThat(request.getAttribute("auth.error.exception")).isInstanceOf(exMap.get(ex));
        }
    }

}