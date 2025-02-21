package com.kamcci.modules.auth.engine.filter;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.engine.config.AuthLoginUrlProperty;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.BadInputRequestException;
import com.kamcci.modules.auth.mock.common.MockAuthenticationManager;
import com.kamcci.modules.auth.mock.handler.MockAuthenticationFailureHandler;
import com.kamcci.modules.auth.mock.handler.MockAuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;
import java.util.*;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthLoginUrlProperty;
import static com.kamcci.modules.auth.mock.common.MockAuthenticationManager.AUTH_EXCEPTION_TYPE;
import static com.kamcci.modules.auth.mock.common.MockAuthenticationManager.RUNTIME_EXCEPTION_TYPE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginRequestAuthFilterTest {
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final AuthLoginUrlProperty authLoginUrlProperty = getAuthLoginUrlProperty();
    private MockAuthenticationManager authenticationManager;
    private MockAuthenticationSuccessHandler authenticationSuccessHandler;
    private MockAuthenticationFailureHandler authenticationFailureHandler;
    // 테스트 대상
    private LoginRequestAuthFilter loginRequestAuthFilter;

    @BeforeEach
    void 테스트_대상_초기화() {
        authenticationManager = new MockAuthenticationManager();
        authenticationSuccessHandler = new MockAuthenticationSuccessHandler();
        authenticationFailureHandler = new MockAuthenticationFailureHandler();
        // 테스트 대상
        loginRequestAuthFilter = new LoginRequestAuthFilter(authLoginUrlProperty.process(), authenticationManager,
                authenticationSuccessHandler, authenticationFailureHandler);
    }

    @Test
    void 로그인_요청_성공() {
        // given
        request.setContent("{\"username\":\"username\", \"password\":\"13\"}".getBytes());

        // when
        loginRequestAuthFilter.attemptAuthentication(request, response);

        // then
        assertThat(authenticationManager.executeCnt).isOne();
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

        // g1. AuthInternalException 반환 설정
        authenticationManager.exceptionType = AUTH_EXCEPTION_TYPE;

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

        // g1. RuntimeException 반환 설정
        authenticationManager.exceptionType = RUNTIME_EXCEPTION_TYPE;

        // when & then
        assertThrows(RuntimeException.class, () -> {
            loginRequestAuthFilter.attemptAuthentication(request, response);
        });
    }

    @Test
    void 로그인_성공_핸들러_호출() throws ServletException, IOException {
        // given
        loginRequestAuthFilter.successfulAuthentication(request, response, null, null);

        // then
        assertThat(authenticationSuccessHandler.executeCnt).isOne();
    }

    @Test
    void 로그인_성공_핸들러_auth_정보_전달_호출() throws ServletException, IOException {
        // given
        JwtAuthenticationToken authResult = new JwtAuthenticationToken(null, null, null);
        Map<String, Object> newDetails = new HashMap<>();
        newDetails.put(UserId.ATTR_NAME, UUID.randomUUID());
        authResult.setDetails(newDetails);

        // when
        loginRequestAuthFilter.successfulAuthentication(request, response, null, authResult);

        // then
        assertThat(authenticationSuccessHandler.executeCnt).isOne();
    }

    @Test
    void 로그인_실패_핸들러_호출() throws ServletException, IOException {
        // given
        loginRequestAuthFilter.unsuccessfulAuthentication(request, response, null);

        // then
        assertThat(authenticationFailureHandler.executeCnt).isOne();
    }
}