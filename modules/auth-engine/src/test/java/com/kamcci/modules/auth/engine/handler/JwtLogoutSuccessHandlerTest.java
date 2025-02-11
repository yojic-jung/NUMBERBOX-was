package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.stub.MockApplicationEventPublisher;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static org.assertj.core.api.Assertions.assertThat;

class JwtLogoutSuccessHandlerTest {
    // 테스트 대상
    private final ApplicationEventPublisher eventPublisher = new MockApplicationEventPublisher();
    private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler = new JwtLogoutSuccessHandler(eventPublisher);
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    void 로그아웃_성공() {
        // when
        jwtLogoutSuccessHandler.onLogoutSuccess(request, response, null);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void 로그아웃_성공_쿠키_제거() {
        // given
        request.setCookies(new Cookie("refresh-token", "refresh-token"));

        // when
        jwtLogoutSuccessHandler.onLogoutSuccess(request, response, null);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
    }
}