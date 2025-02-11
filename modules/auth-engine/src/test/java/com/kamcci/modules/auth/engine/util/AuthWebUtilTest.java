package com.kamcci.modules.auth.engine.util;

import com.kamcci.modules.auth.engine.exception.AuthInternalException;
import com.kamcci.modules.auth.stub.MockHttpServletExceptionResponse;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.UnsupportedEncodingException;

import static com.kamcci.modules.auth.engine.util.AuthWebUtil.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthWebUtilTest {
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    void 에러_응답형식_테스트_성공() throws UnsupportedEncodingException {
        // given
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String msg = "Invalid input";

        // when
        responseErrMsg(response, httpStatus, msg);

        // then
        assertThat(response.getStatus()).isEqualTo(httpStatus.value());
        assertThat(response.getContentAsString()).contains(msg);
    }

    @Test
    void 에러_응답형식_rawStatus_테스트_성공() throws UnsupportedEncodingException {
        // given
        int statusCode = HttpStatus.BAD_REQUEST.value();
        String msg = "Invalid input";

        // when
        responseErrMsg(response, statusCode, msg);

        // then
        assertThat(response.getStatus()).isEqualTo(statusCode);
        assertThat(response.getContentAsString()).contains(msg);
    }

    @Test
    void 성공_응답형식_테스트_성공() throws UnsupportedEncodingException {
        // given
        String msg = "input";

        // when
        responseOK(response, msg);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(msg);
    }

    @Test
    void path_경로추출_성공() throws UnsupportedEncodingException {
        // given
        String msg = "input";
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        // when
        responseOK(response, msg);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains(msg);
    }

    @Test
    void 응답_반환_중_IOException_실패() {
        // given
        MockHttpServletExceptionResponse mockResponse = new MockHttpServletExceptionResponse();
        String msg = "input";

        // when
        assertThrows(AuthInternalException.class, () -> {
            responseOK(mockResponse, msg);
        });
    }

    @Test
    void 쿠키값_조회_성공() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String cookieName = "name";
        String cookieValue = "value";
        request.setCookies(new Cookie(cookieName, cookieValue));

        // when
        String actualCookie = getCookieValue(request, cookieName);

        // then
        assertThat(cookieValue).isEqualTo(actualCookie);
    }

    @Test
    void 미존재_쿠키_조회_성공() {
        // given
        MockHttpServletRequest request = new MockHttpServletRequest();
        String cookieName = "name";

        // when
        String actualCookie = getCookieValue(request, cookieName);

        // then
        assertThat(actualCookie).isNull();
    }

    @Test
    void 쿠키생성_성공() {
        // given
        String cookieName = "name";
        String cookieValue = "value";
        String path = "/path";
        boolean httpOnly = true;
        boolean secure = true;
        int maxAge = 1000;

        // when
        Cookie cookie = makeCookie(cookieName, cookieValue, path, httpOnly, secure, maxAge);

        // then
        assertThat(cookie.getName()).isEqualTo(cookieName);
        assertThat(cookie.getValue()).isEqualTo(cookieValue);
        assertThat(cookie.getPath()).isEqualTo(path);
        assertThat(cookie.isHttpOnly()).isEqualTo(httpOnly);
        assertThat(cookie.getSecure()).isEqualTo(secure);
        assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
    }

    @Test
    void 쿠키생성_값만_설정_성공() {
        // given
        String cookieName = "name";
        String cookieValue = "value";
        int maxAge = 1000;

        // when
        Cookie cookie = makeCookie(cookieName, cookieValue, maxAge);

        // then
        assertThat(cookie.getName()).isEqualTo(cookieName);
        assertThat(cookie.getValue()).isEqualTo(cookieValue);
        assertThat(cookie.getMaxAge()).isEqualTo(maxAge);
    }
}