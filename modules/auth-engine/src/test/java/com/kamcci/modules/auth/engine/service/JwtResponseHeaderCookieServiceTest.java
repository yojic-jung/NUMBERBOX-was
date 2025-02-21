package com.kamcci.modules.auth.engine.service;

import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import com.kamcci.modules.auth.mock.common.MockApplicationEventPublisher;
import com.kamcci.modules.auth.mock.util.MockAuthTokenUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.UUID;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthJwtProperty;
import static com.kamcci.modules.auth.control.config.AuthConstantConfig.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class JwtResponseHeaderCookieServiceTest {
    // 테스트 데이터
    String oldAccessToken = "11cf5466-cda8-ea4d-9bc7-037cb86fdb20";
    String oldRefreshToken = "11cf5466-cda8-ea4d-9bc7-037cb86fdb20";
    MockHttpServletRequest request;
    MockHttpServletResponse response;
    // 스텁
    private AuthTokenUtil authTokenUtil;
    private MockApplicationEventPublisher applicationEventPublisher;
    // 타깃
    private JwtResponseHeaderCookieService jwtResponseHeaderCookieService;

    @BeforeEach
    void 테스트_데이터_초기화() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        authTokenUtil = new MockAuthTokenUtil();
        applicationEventPublisher = new MockApplicationEventPublisher();
        jwtResponseHeaderCookieService = new JwtResponseHeaderCookieService(authTokenUtil, applicationEventPublisher,
                getAuthJwtProperty());
    }

    @Test
    void 액세스토큰_초기화_성공() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        assertThat(response.getHeader(ACCESS_TOKEN_NAME)).contains(TOKEN_STANDARD_PREFIX);
        assertThat(applicationEventPublisher.executeCnt).isZero();
    }

    @Test
    void 액세스토큰_초기화_실패() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        HttpServletResponse servletResponse =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        assertThat(servletResponse).isNull();
        assertThat(applicationEventPublisher.executeCnt).isZero();
    }

    @Test
    void 리프레시토큰_초기화_성공() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, oldRefreshToken);

        // then
        assertThat(applicationEventPublisher.executeCnt).isOne();
    }

    @Test
    void 기존_리프레시_토큰_미존재로_초기화_실패() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        assertThat(applicationEventPublisher.executeCnt).isZero();
    }

    @Test
    void 토큰_만들어_응답_반환_성공() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken("", UUID.randomUUID(), new ArrayList<>());

        // then
        assertThat(applicationEventPublisher.executeCnt).isOne();
    }

    @Test
    void 토큰_만들어_응답_반환_실패() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.responseAuthToken("", UUID.randomUUID(), new ArrayList<>());

        // then
        HttpServletResponse servletResponse =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        assertThat(applicationEventPublisher.executeCnt).isOne();
        assertThat(servletResponse).isNull();
    }

    @Test
    void 전달받은_토큰_응답_반환_성공() {
        // given
        request.setParameter(LOGIN_KEEP_ATTR, LOGIN_KEEP_VAL);
        ServletRequestAttributes attributes = new ServletRequestAttributes(request, response);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.setTokenToResponse("", "", new ArrayList<>());

        // then
        assertThat(response.getHeader(ACCESS_TOKEN_NAME)).contains(TOKEN_STANDARD_PREFIX);
    }

    @Test
    void 전달받은_토큰_응답_반환_실패() {
        // given
        ServletRequestAttributes attributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(attributes);

        // when
        jwtResponseHeaderCookieService.setTokenToResponse("", "", new ArrayList<>());

        // then
        HttpServletResponse servletResponse =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        assertThat(servletResponse).isNull();
    }
}