package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.exception.RefreshTokenNullException;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import com.kamcci.modules.auth.engine.exception.TokenOwnerNotMatchingException;
import com.kamcci.modules.auth.stub.MockAuthTokenUtil;
import com.kamcci.modules.auth.stub.MockJwtRequestUserDetailService;
import com.kamcci.modules.auth.stub.MockUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.Map;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.FAIL_STRING;
import static com.kamcci.modules.auth.constant.MockAuthTestConstant.FAIL_TOKEN;
import static com.kamcci.modules.auth.dummy.AuthUserDummyData.getAuthUserDetail;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JwtRequestAuthProviderTest {
    private final Map<String, Object> details = new HashMap<>();
    private JwtAuthenticationToken authentication;
    // 테스트 대상
    private MockUserDetailsService userDetailsService;
    private MockJwtRequestUserDetailService jwtRequestUserDetailService;
    private MockAuthTokenUtil authTokenUtil;
    private JwtRequestAuthProvider jwtRequestAuthProvider;

    @BeforeEach
    void 테스트_초기화() {
        // 테스트 대상
        userDetailsService = new MockUserDetailsService();
        jwtRequestUserDetailService = new MockJwtRequestUserDetailService();
        authTokenUtil = new MockAuthTokenUtil();
        jwtRequestAuthProvider = new JwtRequestAuthProvider(userDetailsService, jwtRequestUserDetailService,
                authTokenUtil);
        // 데이터 초기화
        details.put("refreshToken", "refreshToken");
    }

    @Test
    void 인증_실패_refreshToken_미존재() {
        // given
        authentication = new JwtAuthenticationToken("", null, null);

        // when & then
        assertThrows(RefreshTokenNullException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 만료된_리프레시토큰_재발급_실패() {
        // given - 재발급 불가 설정
        authentication = new JwtAuthenticationToken(FAIL_STRING, null, null);
        details.put("refreshToken", "임의값");
        authentication.setDetails(details);

        // when & then
        assertThrows(TokenExpirationException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 만료된_리프레시토큰_재발급_성공() {
        // given
        final String accessTokenUserId = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20";
        authentication = new JwtAuthenticationToken(accessTokenUserId, null, null);
        details.put("refreshToken", accessTokenUserId);
        authentication.setDetails(details);

        // when
        Authentication auth = jwtRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> authDetails = (Map<String, Object>) auth.getDetails();
        String oldRefreshToken = (String) authDetails.get("oldRefreshToken");
        assertThat(oldRefreshToken).isEqualTo(accessTokenUserId);
    }

    @Test
    void 인증_실패_비활성_계정() {
        // given
        authentication = new JwtAuthenticationToken(FAIL_TOKEN, null, null);
        details.put("refreshToken", "refreshToken");
        authentication.setDetails(details);

        // when & then
        assertThrows(DisabledException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_실패_토큰소유자_불일치() {
        // given
        final String accessTokenUserId = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20";
        final String refreshTokenUserId = "10CA3122-CDA8-EA4D-9BC7-037CB86FDB20";
        authentication = new JwtAuthenticationToken(accessTokenUserId, null, null);
        details.put("refreshToken", refreshTokenUserId);
        authentication.setDetails(details);

        assertThrows(TokenOwnerNotMatchingException.class, () -> {
            jwtRequestAuthProvider.authenticate(authentication);
        });
    }

    @Test
    void 인증_성공() {
        // given
        final String accessTokenUserId = "10ed5466-cda8-ea4d-9bc7-037cb86fdb20";
        authentication = new JwtAuthenticationToken(accessTokenUserId, null, null);
        details.put("refreshToken", accessTokenUserId);
        authentication.setDetails(details);
        authTokenUtil.isExpire = false;

        // when
        Authentication actualAuth = jwtRequestAuthProvider.authenticate(authentication);

        // then
        Map<String, Object> detail = (Map<String, Object>) actualAuth.getDetails();
        final AuthUserDetail user = getAuthUserDetail();
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