package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.mock.service.MockUserDetailsService;
import com.kamcci.modules.auth.mock.util.MockPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Map;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.FAIL_STRING;
import static com.kamcci.modules.auth.constant.MockAuthTestConstant.NULL_USER;
import static com.kamcci.modules.auth.sample.AuthUserSampleData.AUTH_USER_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        authentication = new UsernamePasswordAuthenticationToken("user", "password");

        // when
        assertThrows(BadCredentialsException.class, () -> {
            loginRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_실패_비활성_계정() {
        // given
        authentication = new UsernamePasswordAuthenticationToken(FAIL_STRING, "");

        // when
        assertThrows(DisabledException.class, () -> {
            loginRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_성공() {
        // given
        authentication = new UsernamePasswordAuthenticationToken("user", "");

        // when
        Authentication actualAuth = loginRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> authDetails = (Map<String, Object>) actualAuth.getDetails();
        assertThat(authDetails).containsEntry(UserId.ATTR_NAME, AUTH_USER_ID);
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