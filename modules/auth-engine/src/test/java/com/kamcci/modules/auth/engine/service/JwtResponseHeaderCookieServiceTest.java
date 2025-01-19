package com.kamcci.modules.auth.engine.service;

import com.kamcci.modules.auth.engine.config.AuthJwtProperty;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.UUID;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthJwtProperty;
import static com.kamcci.modules.auth.control.config.AuthConstantConfig.LOGIN_KEEP_ATTR;
import static com.kamcci.modules.auth.control.config.AuthConstantConfig.LOGIN_KEEP_VAL;
import static org.mockito.Mockito.*;

class JwtResponseHeaderCookieServiceTest {
    private final AuthTokenUtil authTokenUtil = mock();
    private final AuthJwtProperty authJwtProperty = getAuthJwtProperty();
    private final JwtResponseHeaderCookieService jwtResponseHeaderCookieService =
            new JwtResponseHeaderCookieService(authTokenUtil, mock(), authJwtProperty);
    // 테스트 데이터
    String oldAccessToken = "accessToken";
    String oldRefreshToken = "refreshToken";
    HttpServletRequest request = mock();
    HttpServletResponse response = mock();
    ServletRequestAttributes attributes = mock();

    @Test
    void 액세스토큰_초기화_성공() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getResponse()).thenReturn(response);
        when(authTokenUtil.reCreateAccessToken(oldAccessToken)).thenReturn("123");

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        verify(response).setHeader(any(), any());
    }

    @Test
    void 액세스토큰_초기화_실패() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getResponse()).thenReturn(null);

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        verify(response, never()).setHeader(any(), any());
    }

    @Test
    void 리프레시토큰_초기화_성공() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getResponse()).thenReturn(response);
        when(authTokenUtil.reCreateAccessToken(oldAccessToken)).thenReturn("123");
        when(authTokenUtil.reCreateRefreshToken(oldRefreshToken)).thenReturn("123");

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, oldRefreshToken);

        // then
        verify(authTokenUtil).getUserId(any());
    }

    @Test
    void 기존_리프레시_토큰_미존재로_초기화_실패() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getResponse()).thenReturn(response);
        when(authTokenUtil.reCreateAccessToken(oldAccessToken)).thenReturn("123");

        // when
        jwtResponseHeaderCookieService.responseAuthToken(oldAccessToken, null);

        // then
        verify(authTokenUtil, never()).reCreateRefreshToken(oldRefreshToken);
    }

    @Test
    void 토큰_만들어_응답_반환_성공() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getRequest()).thenReturn(request);
        when(attributes.getResponse()).thenReturn(response);
        when(request.getParameter(LOGIN_KEEP_ATTR)).thenReturn("");

        // when
        jwtResponseHeaderCookieService.responseAuthToken("", UUID.randomUUID(), new ArrayList<>());

        // then
        verify(response, atLeast(1)).setHeader(any(), any());
    }

    @Test
    void 토큰_만들어_응답_반환_실패() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getRequest()).thenReturn(request);

        // when
        jwtResponseHeaderCookieService.responseAuthToken("", UUID.randomUUID(), new ArrayList<>());

        // then
        verify(response, never()).setHeader(any(), any());
    }

    @Test
    void 전달받은_토큰_응답_반환_성공() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getRequest()).thenReturn(request);
        when(request.getParameter(LOGIN_KEEP_ATTR)).thenReturn(LOGIN_KEEP_VAL);
        when(attributes.getResponse()).thenReturn(response);

        // when
        jwtResponseHeaderCookieService.setTokenToResponse("", "", new ArrayList<>());

        // then
        verify(response, atLeast(1)).setHeader(any(), any());
    }

    @Test
    void 전달받은_토큰_응답_반환_실패() {
        // given
        RequestContextHolder.setRequestAttributes(attributes);
        when(attributes.getRequest()).thenReturn(request);

        // when
        jwtResponseHeaderCookieService.setTokenToResponse("", "", new ArrayList<>());

        // then
        verify(response, never()).setHeader(any(), any());
    }
}