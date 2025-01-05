package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.control.enumeration.UserRoleType;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class LoginSuccessHandlerTest {
    // 테스트 대상
    private final TokenResponseService tokenResponseService = mock();
    private final LoginSuccessHandler loginSuccessHandler = new LoginSuccessHandler(tokenResponseService);
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();

    @Test
    void 로그인_성공() {
        // given
        List<GrantedAuthority> authorityList = new ArrayList<>();
        authorityList.add(new SimpleGrantedAuthority(UserRoleType.USER.name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken("", "", authorityList);

        // when
        loginSuccessHandler.onAuthenticationSuccess(request, response, authentication);

        // then
        assertThat(response.getStatus()).isEqualTo(200);
    }

}