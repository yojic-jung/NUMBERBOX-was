package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.engine.config.AuthLoginUrlProperty;
import com.kamcci.modules.auth.engine.exception.BadInputRequestException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginFailureHandlerTest {
    private final AuthLoginUrlProperty authLoginUrlProperty = mock();
    private final LoginFailureHandler loginFailureHandler = new LoginFailureHandler(authLoginUrlProperty);

    @Test
    void 인증실패_api_호출_성공() throws ServletException, IOException {
        List<AuthenticationException> exList = new ArrayList<>();
        exList.add(new UsernameNotFoundException(""));
        exList.add(new BadCredentialsException(""));
        exList.add(new DisabledException(""));
        exList.add(new BadInputRequestException(""));
        exList.add(new AccountExpiredException(""));
        HttpServletRequest request = mock();
        HttpServletResponse response = mock();
        RequestDispatcher dispatcher = mock();
        when(request.getRequestDispatcher(any())).thenReturn(dispatcher);

        for(AuthenticationException ex : exList) {
            // when
            loginFailureHandler.onAuthenticationFailure(request, response, ex);

            // then
            verify(request, atLeast(1)).setAttribute(any(), any());
            verify(dispatcher, atLeast(1)).forward(request, response);
        }
    }

}