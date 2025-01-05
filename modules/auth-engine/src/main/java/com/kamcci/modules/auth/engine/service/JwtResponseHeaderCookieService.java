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
    public void refreshAccessToken(String oldAccessToken) {
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        String accessToken = authTokenUtil.createAccessToken(oldAccessToken);
        if(response != null) response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);
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

        String accessToken = authTokenUtil.createAccessToken(email, userId, roleList);
        String refreshToken = authTokenUtil.createRefreshToken();

        // 로그인 성공 이벤트 발행
        publishLoginSuccessEvent(request, userId, refreshToken);

        if(response != null) {
            response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);
            response.setHeader(ROLE_NAME, roleList.toString());
            response.addCookie(makeRefreshTokenCookie(request, refreshToken));
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

        if(response != null) {
            response.setHeader(ACCESS_TOKEN_NAME, TOKEN_STANDARD_PREFIX + " " + accessToken);
            response.setHeader(ROLE_NAME, roleList.toString());
            response.addCookie(makeRefreshTokenCookie(request, refreshToken));
        }
    }

    /**
     * 리프레시 토큰 쿠키 생성
     */
    private Cookie makeRefreshTokenCookie(HttpServletRequest request, String refreshToken) {
        // 리프레시 토큰 유효기간 설정
        final String loginState = request.getParameter(LOGIN_KEEP_ATTR);

        // 클라이언트가 로그인 상태 유지 요청한 경우
        long validTime;
        if(LOGIN_KEEP_VAL.equals(loginState)) {
            validTime = authJwtProperty.refreshToken().keepValidTime() / 1000L;
        } else {
            validTime = authJwtProperty.refreshToken().validTime() / 1000L;
        }
        return AuthWebUtil.makeCookie(REFRESH_TOKEN_NAME, refreshToken, (int) validTime);
    }
}
