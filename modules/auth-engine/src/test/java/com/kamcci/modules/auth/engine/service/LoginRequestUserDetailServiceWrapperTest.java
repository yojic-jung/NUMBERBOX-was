package com.kamcci.modules.auth.engine.service;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getAuthUserInfo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoginRequestUserDetailServiceWrapperTest {
    private final LoginRequestUserDetailService loginRequestUserService = mock();
    private final LoginRequestUserDetailServiceWrapper userDetailsServiceWrapper =
            new LoginRequestUserDetailServiceWrapper(loginRequestUserService);

    @Test
    void 유저조회_성공() {
        // given
        final String username = "abc";
        final AuthUserInfo authUserInfo = getAuthUserInfo();
        when(loginRequestUserService.loadUserByUsername(username)).thenReturn(authUserInfo);

        // when
        User user = this.userDetailsServiceWrapper.loadUserByUsername(username);

        // then
        assertThat(user.getUsername()).isEqualTo(authUserInfo.username());
        assertThat(user.getPassword()).isEqualTo(authUserInfo.password());
    }

    @Test
    void null_유저조회_성공() {
        // given
        final String username = "abc";

        // when
        User user = this.userDetailsServiceWrapper.loadUserByUsername(username);

        // then
        assertThat(user).isNull();
    }

}