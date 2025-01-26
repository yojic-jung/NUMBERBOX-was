package com.kamcci.modules.auth.engine.filter;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.config.AuthConstantConfig;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.TokenException;
import jakarta.servlet.FilterChain;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.TOKEN_STANDARD_PREFIX;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.kotlin.VerificationKt.verify;

class JwtRequestAuthFilterTest {
    // 테스트 대상
    private final AuthenticationManager authenticationManager = mock();
    private final TokenResponseService tokenResponseService = mock();
    final JwtRequestAuthFilter jwtRequestAuthFilter = new JwtRequestAuthFilter(authenticationManager,
            tokenResponseService);
    // 테스트 데이터
    private final MockHttpServletRequest request = new MockHttpServletRequest();
    private final MockHttpServletResponse response = new MockHttpServletResponse();
    private final FilterChain filterChain = mock();

    @Test
    void jwt토큰_미존재_필터_동작_안함() {
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void jwt토큰_null_필터_동작_안함() {
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "null");

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    void jwt토큰_필터_동작() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");
        JwtAuthenticationToken auth = new JwtAuthenticationToken(null, null, null);
        Map<String, Object> newDetails = new HashMap<>();
        newDetails.put(UserId.ATTR_NAME, UUID.randomUUID());
        auth.setDetails(newDetails);
        when(authenticationManager.authenticate(any())).thenReturn(auth);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        verify(tokenResponseService).responseAuthToken(any(), any());
    }

    @Test
    void jwt토큰_필터_TokenException() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");
        when(authenticationManager.authenticate(any())).thenThrow(TokenException.class);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void jwt토큰_필터_DisabledException() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");
        when(authenticationManager.authenticate(any())).thenThrow(DisabledException.class);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void jwt토큰_필터_Exception() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");
        when(authenticationManager.authenticate(any())).thenThrow(RuntimeException.class);

        // when
        jwtRequestAuthFilter.doFilterInternal(request, response, filterChain);

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    void 필터_동작_조건_부합_성공() {
        // given
        request.addHeader(AuthConstantConfig.ACCESS_TOKEN_NAME, "132");

        // when
        boolean isEnable = jwtRequestAuthFilter.shouldNotFilter(request);

        // then
        assertThat(isEnable).isFalse();
    }

    @Test
    void 필터_동작_조건_부합_실패() {
        // when
        boolean isEnable = jwtRequestAuthFilter.shouldNotFilter(request);

        // then
        assertThat(isEnable).isTrue();
    }
}