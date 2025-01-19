package com.kamcci.modules.auth.engine.service;

import com.kamcci.modules.auth.control.config.AuthConstantConfig;
import com.kamcci.modules.auth.control.dto.LoginSuccessEvent;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.engine.config.AuthJwtProperty;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import com.kamcci.modules.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.*;

/**
 * Jwt를 response의 헤더와 쿠키 담음
 */
@Component
public class JwtResponseHeaderCookieService implements TokenResponseService {
    // 리프레시 토큰 유효기간에 30일 더한 만큼 쿠키 수명 설정
    private static final long COOKIE_AGE = 30 * 24 * 60 * 60 * 1000L;
    private final AuthTokenUtil authTokenUtil;
    private final ApplicationEventPublisher eventPublisher;
    private final AuthJwtProperty authJwtProperty;

    public JwtResponseHeaderCookieService(AuthTokenUtil authTokenUtil, ApplicationEventPublisher eventPublisher,
                                          AuthJwtProperty authJwtProperty) {
        this.authTokenUtil = authTokenUtil;
        this.eventPublisher = eventPublisher;
        this.authJwtProperty = authJwtProperty;
    }

    @Override
    public void responseAuthToken(String oldAccessToken, String oldRefreshToken) {
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        // 액세스 토큰 재발급 및 응답
        String accessToken = authTokenUtil.reCreateAccessToken(oldAccessToken);
        if(response != null) response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);

        if(oldRefreshToken != null && response != null) {
            // 리프레시 토큰 재발급 및 응답
            String refreshToken = authTokenUtil.reCreateRefreshToken(oldRefreshToken);
            long validTime = authTokenUtil.getValidTime(oldRefreshToken);
            response.addCookie(makeRefreshTokenCookie(refreshToken, validTime + COOKIE_AGE));

            // 재발급 이벤트 발행
            UUID userId = authTokenUtil.getUserId(accessToken);
            eventPublisher.publishEvent(new LoginSuccessEvent(userId, refreshToken, oldRefreshToken));
        }
    }

    /**
     * accessToken을 만들어 헤더에, refreshToken을 만들어 쿠키에 담음
     */
    @Override
    public void responseAuthToken(String email, UUID userId, List<String> roleList) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        // 토큰 생성
        String accessToken = authTokenUtil.reCreateAccessToken(email, userId, roleList);

        // 리프레시 토큰 유효기간 설정
        long validTime = getRefreshTokenValidTime(request);
        String refreshToken = authTokenUtil.createRefreshToken(validTime);

        // 로그인 성공 이벤트 발행
        publishLoginSuccessEvent(request, userId, refreshToken);

        if(response != null) {
            response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);
            response.setHeader(ROLE_NAME, roleList.toString());
            response.addCookie(makeRefreshTokenCookie(refreshToken, validTime + COOKIE_AGE));
        }
    }

    private void publishLoginSuccessEvent(HttpServletRequest request, UUID userId, String refreshToken) {
        final String remainedRefreshToken = AuthWebUtil.getCookieValue(request, AuthConstantConfig.REFRESH_TOKEN_NAME);
        final LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent(userId, refreshToken, remainedRefreshToken);
        eventPublisher.publishEvent(loginSuccessEvent);
    }

    /**
     * accessToken을 헤더에, refreshToken을 쿠키에 담음
     */
    @Override
    public void setTokenToResponse(String accessToken, String refreshToken, List<String> roleList) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        // 리프레시 토큰 유효기간 설정
        long validTime = getRefreshTokenValidTime(request);

        if(response != null) {
            response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);
            response.setHeader(ROLE_NAME, roleList.toString());
            response.addCookie(makeRefreshTokenCookie(refreshToken, validTime));
        }
    }

    /**
     * 리프레시 토큰 유효기간 반환
     * <p>
     * milleSecond 단위
     */
    private long getRefreshTokenValidTime(HttpServletRequest request) {
        final String loginState = request.getParameter(LOGIN_KEEP_ATTR);

        long validTime;
        if(LOGIN_KEEP_VAL.equals(loginState)) {
            validTime = authJwtProperty.refreshToken().keepValidTime();
        } else {
            validTime = authJwtProperty.refreshToken().validTime();
        }
        return validTime;
    }

    /**
     * 리프레시 토큰 쿠키 생성
     */
    private Cookie makeRefreshTokenCookie(String refreshToken, long validTime) {
        return AuthWebUtil.makeCookie(REFRESH_TOKEN_NAME, refreshToken, (int) (validTime / 1000L));
    }
}
