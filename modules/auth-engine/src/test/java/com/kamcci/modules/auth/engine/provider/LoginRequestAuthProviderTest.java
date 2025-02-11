package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.stub.MockPasswordEncoder;
import com.kamcci.modules.auth.stub.MockUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.NULL_USER;
import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getAuthUserDetail;
import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getDisableAuthUserDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoginRequestAuthProviderTest {
    private Authentication authentication;
    private MockUserDetailsService userDetailsService;
    private MockPasswordEncoder passwordEncoder;
    private LoginRequestAuthProvider loginRequestAuthProvider;

    @BeforeEach
    void 테스트_초기화() {
        userDetailsService = new MockUserDetailsService();
        passwordEncoder = new MockPasswordEncoder();
        loginRequestAuthProvider = new LoginRequestAuthProvider(userDetailsService, passwordEncoder);
    }

    @Test
    void 인증_실패_계정_미존재() {
        // given
        authentication = new UsernamePasswordAuthenticationToken(NULL_USER, "password");

        // when & then
        assertThrows(UsernameNotFoundException.class, () -> {
            loginRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_실패_비밀번호_불일치() {
        // given
        AuthUserDetail userDetail = getAuthUserDetail();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetail);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        // when
        assertThrows(BadCredentialsException.class, () -> {
            loginRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_실패_비활성_계정() {
        // given
        AuthUserDetail userDetail = getDisableAuthUserDetail();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetail);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // when
        assertThrows(DisabledException.class, () -> {
            loginRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_성공() {
        // given
        AuthUserDetail userDetail = getAuthUserDetail();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetail);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        // when
        Authentication actualAuth = loginRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> authDetails = (Map<String, Object>) actualAuth.getDetails();
        assertThat(authDetails).containsEntry(UserId.ATTR_NAME, userDetail.getUserId());
    }

    @Test
    void jwtProvider_활성_조건_성공() {
        // when
        boolean isAble = loginRequestAuthProvider.supports(UsernamePasswordAuthenticationToken.class);

        // then
        assertThat(isAble).isTrue();
    }

    @Test
    void jwtProvider_활성_조건_실패() {
        // when
        boolean isAble = loginRequestAuthProvider.supports(JwtAuthenticationToken.class);

        // then
        assertThat(isAble).isFalse();
    }
}