package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.mock.service.MockTokenResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class LoginSuccessHandlerTest {
    // 테스트 대상 및 더블
    private final TokenResponseService tokenResponseService = new MockTokenResponseService();
    private final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(tokenResponseService);
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    void 로그인_성공() {
        // given - 인증 객체 설정
        // g1. roll 설정
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("", "",
                authorityList);
        // g2. userId 설정
        final Map<String, Object> details = new HashMap<>();
        details.put(UserId.ATTR_NAME, UUID.randomUUID());
        authentication.setDetails(details);

        // when
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
    }

}