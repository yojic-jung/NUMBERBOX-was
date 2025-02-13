package com.kamcci.modules.auth.engine.service;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService;
import com.kamcci.modules.auth.stub.service.MockLoginRequestUserDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.NULL_USER;
import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getAuthUserInfo;
import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestUserDetailServiceWrapperTest {
    private LoginRequestUserDetailService loginRequestUserService;
    private LoginRequestUserDetailServiceWrapper userDetailsServiceWrapper;

    @BeforeEach
    void 테스트_초기화() {
        loginRequestUserService = new MockLoginRequestUserDetailService();
        userDetailsServiceWrapper = new LoginRequestUserDetailServiceWrapper(loginRequestUserService);
    }

    @Test
    void 유저조회_성공() {
        // given
        final String username = "abc";
        final AuthUserInfo authUserInfo = getAuthUserInfo();

        // when
        User user = this.userDetailsServiceWrapper.loadUserByUsername(username);

        // then
        assertThat(user.getUsername()).isEqualTo(authUserInfo.username());
        assertThat(user.getPassword()).isEqualTo(authUserInfo.password());
    }

    @Test
    void null_유저조회_성공() {
        // given
        final String username = NULL_USER;

        // when
        User user = this.userDetailsServiceWrapper.loadUserByUsername(username);

        // then
        assertThat(user).isNull();
    }

}