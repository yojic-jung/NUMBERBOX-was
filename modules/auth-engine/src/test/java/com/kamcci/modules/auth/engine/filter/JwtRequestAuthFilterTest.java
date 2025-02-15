package com.kamcci.modules.auth.engine.filter;

import com.kamcci.modules.auth.control.config.AuthConstantConfig;
import com.kamcci.modules.auth.stub.common.MockAuthenticationManager;
import com.kamcci.modules.auth.stub.common.MockFilterChain;
import com.kamcci.modules.auth.stub.service.MockTokenResponseService;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.TOKEN_STANDARD_PREFIX;
import static com.kamcci.modules.auth.stub.common.MockAuthenticationManager.*;
import static org.assertj.core.api.Assertions.assertThat;

class JwtRequestAuthFilterTest {
    // 테스트 대상
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final FilterChain filterChain = new MockFilterChain();
    private MockAuthenticationManager authenticationManager;
    private MockTokenResponseService tokenResponseService;
    private JwtRequestAuthFilter jwtRequestAuthFilter;

    @BeforeEach
    void 테스트_대상_초기화() {
        authenticationManager = new MockAuthenticationManager();
        tokenResponseService = new MockTokenResponseService();
        jwtRequestAuthFilter = new JwtRequestAuthFilter(authenticationManager, tokenResponseService);
    }

    @Test
    void jwt토큰_미존재_필터_동작_안함() {
        // given - 토큰 미설정
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(authenticationManager.executeCnt).isZero();
    }

    @Test
    void jwt토큰_null_필터_동작_안함() {
        // given - jwt 토큰 null 선언
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "null");

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(authenticationManager.executeCnt).isZero();
    }

    @Test
    void jwt토큰_필터_동작() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(tokenResponseService.executeCnt).isOne();
    }

    @Test
    void jwt토큰_필터_TokenException() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // g1. 스텁에서 TokenException 반환하도록 설정
        authenticationManager.exceptionType = TOKEN_EXCEPTION_TYPE;

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void jwt토큰_필터_DisabledException() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // g1. 스텁에서 DisabledException 반환하도록 설정
        authenticationManager.exceptionType = DISABLE_EXCEPTION_TYPE;

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void jwt토큰_필터_Exception() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // g1. 스텁에서 RuntimeException 반환하도록 설정
        authenticationManager.exceptionType = RUNTIME_EXCEPTION_TYPE;

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void 필터_동작_조건_부합_성공() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // when
        boolean isEnable = jwtRequestAuthFilter.shouldNotFilter(request);

        // then
        assertThat(isEnable).isFalse();
    }

    @Test
    void 필터_동작_조건_부합_실패() {
        // when
        boolean isEnable = jwtRequestAuthFilter.shouldNotFilter(request);

        // then
        assertThat(isEnable).isTrue();
    }
}