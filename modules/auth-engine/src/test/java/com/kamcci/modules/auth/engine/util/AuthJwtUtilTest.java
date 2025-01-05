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
    // 테스트 대상
    private final AuthJwtProperty authJwtProperty = getAuthJwtProperty();
    private final AuthJwtUtil authJwtUtil = new AuthJwtUtil(authJwtProperty);

    @Test
    void 액세스_토큰_생성_성공() {
        // given
        final List<String> roleList = new ArrayList<>();
        roleList.add(ROLE);

        // when
        final String accessToken = authJwtUtil.createAccessToken(EMAIL, UNIQ_ID, roleList);

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(accessToken).getBody();
        assertThat(claims.get(authJwtProperty.email())).isEqualTo(EMAIL);
        assertThat(claims.get(authJwtProperty.id())).isEqualTo(UNIQ_ID.toString());
        assertThat(claims.get(ROLE_NAME)).isEqualTo(roleList);
        assertThat(claims.get(authJwtProperty.domain())).isEqualTo(true);
        assertThat(claims.getIssuer()).isEqualTo(authJwtProperty.issuer());
        assertThat(claims.getAudience()).isEqualTo(authJwtProperty.audience());
        assertThat(claims.getSubject()).isEqualTo(authJwtProperty.accessToken().subject());
        assertThat(claims.getExpiration()).isAfter(new Date()); // Token should have expiration in the future
    }

    @Test
    void 기존_액세스_토큰_정보로_생성_성공() {
        // given
        final List<String> roleList = new ArrayList<>();
        roleList.add(ROLE);
        final String accessToken = authJwtUtil.createAccessToken(EMAIL, UNIQ_ID, roleList);

        // when
        final String newAccessToken = authJwtUtil.createAccessToken(accessToken);

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(newAccessToken)
                .getBody();
        assertThat(claims.get(authJwtProperty.email())).isEqualTo(EMAIL);
        assertThat(claims.get(authJwtProperty.id())).isEqualTo(UNIQ_ID.toString());
        assertThat(claims.get(ROLE_NAME)).isEqualTo(roleList);
    }

    @Test
    void 리프레시_토큰_생성_성공() {
        // when
        final String refreshToken = authJwtUtil.createRefreshToken();

        // then
        Claims claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(refreshToken).getBody();
        assertThat(claims.get(authJwtProperty.domain())).isEqualTo(true);
        assertThat(claims.getIssuer()).isEqualTo(authJwtProperty.issuer());
        assertThat(claims.getAudience()).isEqualTo(authJwtProperty.audience());
        assertThat(claims.getSubject()).isEqualTo(authJwtProperty.refreshToken().subject());
        assertThat(claims.getExpiration()).isAfter(new Date());
        assertThat(claims.getIssuedAt()).isBeforeOrEqualTo(new Date());
    }

    @Test
    void 만료된_토큰으로부터_이메일_추출_성공() {
        // given
        final String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when
        final String email = authJwtUtil.getEmail(accessToken);

        // then
        assertThat(email).isEqualTo(EMAIL);
    }

    @Test
    void 토큰으로부터_이메일_추출_실패() {
        // given
        final String accessToken = "eyJ1hbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.getEmail(accessToken);
        });
    }

    @Test
    void 만료된_토큰으로부터_id_추출_성공() {
        // given
        final String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when
        final UUID userUniqId = authJwtUtil.getUserUniqId(accessToken);

        // then
        assertThat(userUniqId).isEqualTo(UUID.fromString("688fa23b-194a-46f0-bc31-14601864f802"));
    }

    @Test
    void 토큰으로부터_id_추출_실패() {
        // given
        final String accessToken = "111eyJ1hbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.getUserUniqId(accessToken);
        });
    }

    @Test
    void 만료된_토큰으로부터_권한_추출_성공() {
        // given
        final String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when
        final List<String> roleList = authJwtUtil.getRoles(accessToken);

        // then
        assertThat(roleList.get(0)).isEqualTo(ROLE);

    }

    @Test
    void 토큰으로부터_권한_추출_실패() {
        // given
        final String accessToken = "111eyJ1hbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

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
        final String accessToken = authJwtUtil.createAccessToken(EMAIL, UNIQ_ID, roleList);

        // when
        assertDoesNotThrow(() -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }

    @Test
    void 토큰_만료로_유효성_실패() {
        // given
        final String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when & then
        assertThrows(TokenExpirationException.class, () -> {
            authJwtUtil.checkValidToken(accessToken, true);
        });
    }

    @Test
    void 토큰_만료_유효성_무시_성공() {
        // given
        final String accessToken = "eyJhbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when & then
        assertDoesNotThrow(() -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }

    @Test
    void 토큰_유효성_실패() {
        // given
        final String accessToken = "111eyJ1hbGciOiJIUzI1NiJ9" +
                ".eyJlbWFpbCI6ImVtYWlsQGVtYWlsLmNvbSIsImlkIjoiNjg4ZmEyM2ItMTk0YS00NmYwLWJjMzEtMTQ2MDE4NjRmODAyIiwicm9sZXMiOlsiVVNFUiJdLCJkb21haW4uY29tIjp0cnVlLCJpc3MiOiJpc3N1ZXIiLCJzdWIiOiJzdWJqZWN0IiwiYXVkIjoiYXVkaWVuY2UiLCJpYXQiOjE3MzYwNDkxMDIsImV4cCI6MTczNjA0OTEwMn0.Q_IGuai54SFIk5eTyJMe923JkIEe5xIMBCZVBAeIo3o";

        // when & then
        assertThrows(Exception.class, () -> {
            authJwtUtil.checkValidToken(accessToken, false);
        });
    }
}