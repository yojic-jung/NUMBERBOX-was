package com.kamcci.modules.auth.engine.filter;

import com.kamcci.modules.auth.engine.config.AuthLoginUrlProperty;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.AuthInternalException;
import com.kamcci.modules.auth.engine.exception.BadInputRequestException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthLoginUrlProperty;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoginRequestAuthFilterTest {
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    // 모킹
    private final AuthenticationManager authenticationManager = mock();
    private final AuthenticationSuccessHandler authenticationSuccessHandler = mock();
    private final AuthenticationFailureHandler authenticationFailureHandler = mock();
    private final AuthLoginUrlProperty authLoginUrlProperty = getAuthLoginUrlProperty();
    // 테스트 대상
    LoginRequestAuthFilter loginRequestAuthFilter = new LoginRequestAuthFilter(authLoginUrlProperty.process(),
            authenticationManager, authenticationSuccessHandler, authenticationFailureHandler);

    @Test
    void 로그인_요청_성공() {
        // given
        request.setContent("{\"username\":\"username\", \"password\":\"13\"}".getBytes());

        // when
        loginRequestAuthFilter.attemptAuthentication(request, response);

        // then
        verify(authenticationManager).authenticate(any());
    }

    @Test
    void 로그인_요청_실패_BadInputRequestException() {
        // given
        List<String> requestBodyList = new ArrayList<>();
        requestBodyList.add("{\"username\":null, \"password\":null}");
        requestBodyList.add("{\"username\":\"123\", \"password\":null}");
        requestBodyList.add("{\"username\":null, \"password\":\"123\"}");

        for(String reqBody : requestBodyList) {
            request.setContent(reqBody.getBytes());

            // when & then
            assertThrows(BadInputRequestException.class, () -> {
                loginRequestAuthFilter.attemptAuthentication(request, response);
            });
        }

    }

    @Test
    void 로그인_요청_실패_AuthenticationException() {
        // given
        request.setContent("{\"username\":\"username\", \"password\":\"13\"}".getBytes());
        when(authenticationManager.authenticate(any())).thenThrow(new AuthInternalException(""));

        // when & then
        assertThrows(AuthenticationException.class, () -> {
            loginRequestAuthFilter.attemptAuthentication(request, response);
        });
    }

    @Test
    void 로그인_요청_실패_JsonParseException() {
        // given
        request.setContent("{\"username\":\"username\", \"password\":\"13\"".getBytes());

        // when & then
        assertThrows(BadInputRequestException.class, () -> {
            loginRequestAuthFilter.attemptAuthentication(request, response);
        });
    }

    @Test
    void 로그인_요청_실패_Exception() {
        // given
        request.setContent("{\"username\":\"username\", \"password\":\"13\"}".getBytes());
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException(""));

        // when & then
        assertThrows(AuthInternalException.class, () -> {
            loginRequestAuthFilter.attemptAuthentication(request, response);
        });
    }

    @Test
    void 로그인_성공_핸들러_호출() throws ServletException, IOException {
        // given
        loginRequestAuthFilter.successfulAuthentication(request, response, null, null);

        // then
        verify(authenticationSuccessHandler).onAuthenticationSuccess(request, response, null);
    }

    @Test
    void 로그인_성공_핸들러_auth_정보_전달_호출() throws ServletException, IOException {
        // given
        JwtAuthenticationToken authResult = new JwtAuthenticationToken(null, null, null);
        loginRequestAuthFilter.successfulAuthentication(request, response, null, authResult);

        // then
        verify(authenticationSuccessHandler).onAuthenticationSuccess(request, response, authResult);
    }

    @Test
    void 로그인_실패_핸들러_호출() throws ServletException, IOException {
        // given
        loginRequestAuthFilter.unsuccessfulAuthentication(request, response, null);

        // then
        verify(authenticationFailureHandler, atLeast(1)).onAuthenticationFailure(request, response, null);
    }
}