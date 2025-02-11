package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.exception.RefreshTokenNullException;
import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import com.kamcci.modules.auth.engine.exception.TokenOwnerNotMatchingException;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getAuthUserDetail;
import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getDisableAuthUserDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtRequestAuthProviderTest {
    // 테스트 데이터
    final String accessTokenUserId = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20";
    final String refreshTokenUserId = "10CA3122-CDA8-EA4D-9BC7-037CB86FDB20";
    final Authentication authentication = mock();
    // 테스트 대상
    private final UserDetailsService userDetailsService = mock();
    private final JwtRequestUserDetailService jwtRequestUserDetailService = mock();
    private final AuthTokenUtil authTokenUtil = mock();
    private final JwtRequestAuthProvider jwtRequestAuthProvider = new JwtRequestAuthProvider(userDetailsService,
            jwtRequestUserDetailService, authTokenUtil);
    Map<String, Object> details = new HashMap<>();

    @BeforeEach
    void 테스트데이터_초기화() {
        details.put("refreshToken", "refreshToken");
    }

    @Test
    void 인증_실패_refreshToken_미존재() {
        assertThrows(RefreshTokenNullException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 만료된_리프레시토큰_재발급_실패() {
        // given
        when(authentication.getPrincipal()).thenReturn("accessToken");
        details.put("refreshToken", "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJuc29vaGFrLmNvbSI6dHJ1ZSwiaXNzIjoibnNvb2hhayIsInN1YiI6Im5zb29oYWtSZWZyZXNoVG9rZW4iLCJhdWQiOiJ1c2VyIiwiZXhwIjoxNzM3MjUxNTg0LCJpYXQiOjE3MzcyNTE1MzR9.KLnvJ0zXVkTU4dLiS5pCigvlstJ0onYzVV8UVhkxs-s");
        when(authentication.getDetails()).thenReturn(details);
        when(authTokenUtil.isExpiredToken(any())).thenReturn(true);
        when(authTokenUtil.getUserId(any())).thenReturn(UUID.fromString(accessTokenUserId));
        // 재발급 불가 설정
        when(jwtRequestUserDetailService.canReCreateRefreshToken(any())).thenReturn(false);

        // when & then
        assertThrows(TokenExpirationException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 만료된_리프레시토큰_재발급_성공() {
        // given
        UUID userId = UUID.fromString("10ed5466-cda8-ea4d-9bc7-037cb86fdb20");
        String refreshToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJuc29vaGFrLmNvbSI6dHJ1ZSwiaXNzIjoibnNvb2hhayIsInN1YiI6Im5zb29oYWtSZWZyZXNoVG9rZW4iLCJhdWQiOiJ1c2VyIiwiZXhwIjoxNzM3MjUxNTg0LCJpYXQiOjE3MzcyNTE1MzR9.KLnvJ0zXVkTU4dLiS5pCigvlstJ0onYzVV8UVhkxs-s";
        when(authentication.getPrincipal()).thenReturn("accessToken");
        details.put("refreshToken", refreshToken);
        when(authentication.getDetails()).thenReturn(details);
        when(authTokenUtil.isExpiredToken(any())).thenReturn(true);
        when(authTokenUtil.getUserId(any())).thenReturn(userId);
        when(jwtRequestUserDetailService.loadUserIdByRefreshToken(any())).thenReturn(userId);
        final AuthUserDetail user = getAuthUserDetail();
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);
        // 재발급 가능 설정
        when(jwtRequestUserDetailService.canReCreateRefreshToken(any())).thenReturn(true);

        // when
        Authentication auth = jwtRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> authDetails = (Map<String, Object>) auth.getDetails();
        String oldRefreshToken = (String) authDetails.get("oldRefreshToken");
        assertThat(oldRefreshToken).isEqualTo(refreshToken);
    }

    @Test
    void 인증_실패_토큰소유자_불일치() {
        when(authentication.getPrincipal()).thenReturn("accessToken");
        when(authentication.getDetails()).thenReturn(details);
        when(authTokenUtil.getUserId(any())).thenReturn(UUID.fromString(accessTokenUserId));
        when(jwtRequestUserDetailService.loadUserIdByRefreshToken(any())).thenReturn(UUID.fromString(refreshTokenUserId));

        assertThrows(TokenOwnerNotMatchingException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_실패_비활성_계정() {
        final AuthUserDetail disableUser = getDisableAuthUserDetail();
        when(authentication.getPrincipal()).thenReturn("accessToken");
        when(authentication.getDetails()).thenReturn(details);
        when(authTokenUtil.getUserId(any())).thenReturn(UUID.fromString(accessTokenUserId));
        when(jwtRequestUserDetailService.loadUserIdByRefreshToken(any())).thenReturn(UUID.fromString(accessTokenUserId));
        when(userDetailsService.loadUserByUsername(any())).thenReturn(disableUser);

        assertThrows(DisabledException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_성공() {
        final AuthUserDetail user = getAuthUserDetail();
        when(authentication.getPrincipal()).thenReturn("accessToken");
        when(authentication.getDetails()).thenReturn(details);
        when(authTokenUtil.getUserId(any())).thenReturn(UUID.fromString(accessTokenUserId));
        when(jwtRequestUserDetailService.loadUserIdByRefreshToken(any())).thenReturn(UUID.fromString(accessTokenUserId));
        when(userDetailsService.loadUserByUsername(any())).thenReturn(user);

        // when
        Authentication actualAuth = jwtRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> detail = (Map<String, Object>) actualAuth.getDetails();
        assertThat(detail).containsEntry(UserId.ATTR_NAME, user.getUserId());
    }

    @Test
    void jwtProvider_활성_조건_성공() {
        // when
        boolean isAble = jwtRequestAuthProvider.supports(JwtAuthenticationToken.class);

        // then
        assertThat(isAble).isTrue();
    }

    @Test
    void jwtProvider_활성_조건_실패() {
        // when
        boolean isAble = jwtRequestAuthProvider.supports(UsernamePasswordAuthenticationToken.class);

        // then
        assertThat(isAble).isFalse();
    }
}