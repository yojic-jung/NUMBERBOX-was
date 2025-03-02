package com.kamcci.modules.auth.engine.util;

import com.kamcci.modules.auth.engine.config.AuthJwtProperty;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthJwtProperty;
import static com.kamcci.modules.auth.control.config.AuthConstantConfig.ROLE_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AuthJwtUtilTest {
    // 테스트 데이터
    static final String EMAIL = "email@email.com";
    static final UUID UNIQ_ID = UUID.randomUUID();
    static final String ROLE = "USER";
    static final String EXPIRE_TOKEN = "eyJhbGciOiJIUzI1NiJ9" +
            ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";
    static final String INVALID_TOKEN = "111eyJ1hbGciOiJIUzI1NiJ9" +
            ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";
    static final String VALID_TOKEN = "eyJ1hbGciOiJIUzI1NiJ9" +
            ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";
    // 테스트 대상
    private final AuthJwtProperty authJwtProperty = getAuthJwtProperty();
    private final AuthJwtUtil authJwtUtil = new AuthJwtUtil(authJwtProperty);

    @Test
    void 액세스_토큰_생성_성공() {
        // given
        final List<String> roleList = new ArrayList<>();
        roleList.add(ROLE);

        // when
        final String accessToken = authJwtUtil.reCreateAccessToken(EMAIL, UNIQ_ID, roleList);

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(accessToken).getBody();
        assertAccessTokenClaims(claims, roleList);
    }

    private void assertAccessTokenClaims(Claims claims, List<String> roleList) {
        assertThat(claims).containsEntry(authJwtProperty.email(), EMAIL)
                .containsEntry(authJwtProperty.id(), UNIQ_ID.toString()) //
                .containsEntry(ROLE_NAME, roleList) //
                .containsEntry(authJwtProperty.domain(), true) //
                .containsEntry(authJwtProperty.email(), EMAIL);
        assertThat(claims.getIssuer()).isEqualTo(authJwtProperty.issuer());
        assertThat(claims.getAudience()).isEqualTo(authJwtProperty.audience());
        assertThat(claims.getSubject()).isEqualTo(authJwtProperty.accessToken().subject());
        assertThat(claims.getExpiration()).isAfter(new Date());
    }

    @Test
    void 기존_액세스_토큰_정보로_생성_성공() {
        // given
        final List<String> roleList = new ArrayList<>();
        roleList.add(ROLE);
        final String accessToken = authJwtUtil.reCreateAccessToken(EMAIL, UNIQ_ID, roleList);

        // when
        final String newAccessToken = authJwtUtil.reCreateAccessToken(accessToken);

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(newAccessToken)
                .getBody();
        assertThat(claims).containsEntry(authJwtProperty.email(), EMAIL) //
                .containsEntry(authJwtProperty.id(), UNIQ_ID.toString()) //
                .containsEntry(ROLE_NAME, roleList);
    }

    @Test
    void 리프레시_토큰_생성_성공() {
        // when
        final long validTime = 10000L;
        final String refreshToken = authJwtUtil.createRefreshToken(validTime);

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(refreshToken).getBody();
        assertRefreshTokenClaims(claims, validTime);
    }

    private void assertRefreshTokenClaims(Claims claims, long validTime) {
        assertThat(claims).containsEntry(authJwtProperty.domain(), true);
        assertThat(claims.getIssuer()).isEqualTo(authJwtProperty.issuer());
        assertThat(claims.getAudience()).isEqualTo(authJwtProperty.audience());
        assertThat(claims.getSubject()).isEqualTo(authJwtProperty.refreshToken().subject());
        assertThat(claims.getExpiration()).isBefore(new Date(new Date().getTime() + validTime));
        assertThat(claims.getIssuedAt()).isBeforeOrEqualTo(new Date());
    }

    @Test
    void 리프레시_토큰_재발급_성공() {
        // given
        String expireToken = EXPIRE_TOKEN;
        // when
        final String refreshToken = authJwtUtil.reCreateRefreshToken(expireToken);

        // then
        long expectedValidTime = authJwtUtil.getValidTime(expireToken);
        long actualValidTime = authJwtUtil.getValidTime(refreshToken);
        assertThat(actualValidTime).isEqualTo(expectedValidTime);
    }

    @Test
    void 만료된_토큰으로부터_이메일_추출_성공() {
        // given
        final String accessToken = EXPIRE_TOKEN;

        // when
        final String email = authJwtUtil.getEmail(accessToken);

        // then
        assertThat(email).isEqualTo(EMAIL);
    }

    @Test
    void 토큰으로부터_이메일_추출_실패() {
        // given
        final String accessToken = VALID_TOKEN;

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.getEmail(accessToken);
        });
    }

    @Test
    void 만료된_토큰으로부터_id_추출_성공() {
        // given
        final String accessToken = EXPIRE_TOKEN;

        // when
        final UUID userUniqId = authJwtUtil.getUserId(accessToken);

        // then
        assertThat(userUniqId).isEqualTo(UUID.fromString("688fa23b-194a-46f0-bc31-14601864f802"));
    }

    @Test
    void 토큰으로부터_id_추출_실패() {
        // given - 유효하지 않은 토큰
        final String accessToken = INVALID_TOKEN;

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.getUserId(accessToken);
        });
    }

    @Test
    void 만료된_토큰으로_유효기간_추출() {
        // given
        long expectedValidTime = 10000L;
        String expireToken = "eyJhbGciOiJIUzI1NiJ9" + // validTime = 1000l인 토큰
                ".eyJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJleHAiOjE3MzcyNTU3NzAsImlhdCI6MTczNzI1NTc2MH0.wMf4Ws6P9ZIY1ZX9KvUBa7yLLBBzM2UnVbosLkzQbpw";

        // when
        final String refreshToken = authJwtUtil.reCreateRefreshToken(expireToken);

        // then
        long actualValidTime = authJwtUtil.getValidTime(refreshToken);
        assertThat(actualValidTime).isEqualTo(expectedValidTime);
    }

    @Test
    void 만료되지_않은_토큰으로_유효기간_추출() {
        // given
        final long validTime = 1000000L;
        final String notExpireToken = authJwtUtil.createRefreshToken(validTime);

        // when
        final String refreshToken = authJwtUtil.reCreateRefreshToken(notExpireToken);

        // then
        long actualValidTime = authJwtUtil.getValidTime(refreshToken);
        assertThat(actualValidTime).isEqualTo(validTime);
    }

    @Test
    void 만료된_토큰으로부터_권한_추출_성공() {
        // given
        final String accessToken = EXPIRE_TOKEN;

        // when
        final List<String> roleList = authJwtUtil.getRoles(accessToken);

        // then
        assertThat(roleList.get(0)).isEqualTo(ROLE);

    }

    @Test
    void 토큰으로부터_권한_추출_실패() {
        // given - 유효하지 않은 토큰
        final String accessToken = INVALID_TOKEN;
        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.getRoles(accessToken);
        });
    }

    @Test
    void 토큰_유효성_성공() {
        // given
        final List<String> roleList = new ArrayList<>();
        roleList.add(ROLE);
        final String accessToken = authJwtUtil.reCreateAccessToken(EMAIL, UNIQ_ID, roleList);

        // when
        assertDoesNotThrow(() -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }

    @Test
    void 토큰_만료로_유효성_실패() {
        // given - 만료된 토큰
        final String accessToken = EXPIRE_TOKEN;

        // when & then
        assertThrows(TokenExpirationException.class, () -> {
            authJwtUtil.checkValidToken(accessToken, true);
        });
    }

    @Test
    void 토큰_만료__유효성_검사_제외_성공() {
        // given - 만료된 토큰
        final String accessToken = EXPIRE_TOKEN;

        // when & then
        assertDoesNotThrow(() -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }

    @Test
    void 토큰_유효성_실패() {
        // given - 유효하지 않은 jwt
        final String accessToken = INVALID_TOKEN;

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }

    @Test
    void 만료된_토큰_체크_성공() {
        // given
        final String expireToken = EXPIRE_TOKEN;
        // when
        boolean isExpire = authJwtUtil.isExpiredToken(expireToken);

        // then
        assertThat(isExpire).isTrue();
    }

    @Test
    void 만료되지_않은_토큰_체크_성공() {
        // given
        final long validTime = 1000000L;
        final String notExpireToken = authJwtUtil.createRefreshToken(validTime);
        // when
        boolean isExpire = authJwtUtil.isExpiredToken(notExpireToken);

        // then
        assertThat(isExpire).isFalse();
    }
}