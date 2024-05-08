package com.numberbox.auth.engine.service;

import com.numberbox.auth.control.config.AuthConstantConfig;
import com.numberbox.auth.control.dto.LoginSuccessEvent;
import com.numberbox.auth.control.service.TokenResponseService;
import com.numberbox.auth.engine.util.AuthTokenUtil;
import com.numberbox.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.UUID;

import static com.numberbox.auth.control.config.AuthConstantConfig.*;

/**
 * Jwt를 response의 헤더와 쿠키 담음
 */
@Component
public class JwtResponseHeaderCookieService implements TokenResponseService {
    private final AuthTokenUtil authTokenUtil;
    private final ApplicationEventPublisher eventPublisher;

    public JwtResponseHeaderCookieService(AuthTokenUtil authTokenUtil, ApplicationEventPublisher eventPublisher) {
        this.authTokenUtil = authTokenUtil;
        this.eventPublisher = eventPublisher;
    }

    /**
     * accessToken을 만들어 헤더에, refreshToken을 만들어 쿠키에 담음
     * todo 제대로 동작하는지 테스트 필요
     */
    @Override
    public void createAndSetTokenToResponse(String email, UUID userId, List<String> roleList) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        String accessToken = authTokenUtil.createAccessToken(email, userId, roleList);
        String refreshToken = authTokenUtil.createRefreshToken();

        // 로그인 성공 이벤트 발행
        publishLoginSuccessEvent(request, userId, refreshToken);

        response.setHeader(ACCESS_TOKEN_NAME, accessToken);
        response.setHeader(ROLE_NAME, roleList.toString());
        response.addCookie(makeRefreshTokenCookie(request, refreshToken));
    }

    // todo 이름 변경?? refreshTokenCreatedEvent
    private void publishLoginSuccessEvent(HttpServletRequest request, UUID userId, String refreshToken){
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

        response.setHeader(ACCESS_TOKEN_NAME, accessToken);
        response.setHeader(ROLE_NAME, roleList.toString());
        response.addCookie(makeRefreshTokenCookie(request, refreshToken));
    }

    /**
     * 리프레시 토큰 쿠키 생성
     */
    private Cookie makeRefreshTokenCookie(HttpServletRequest request, String refreshToken) {
        // 리프레시 토큰 유효기간 설정
        final String loginState = request.getParameter(LOGIN_KEEP_ATTR);

        // 클라이언트가 로그인 상태 유지 요청한 경우
        if (loginState != null && loginState.equals(LOGIN_KEEP_VAL)) {
            return AuthWebUtil.makeCookie(REFRESH_TOKEN_NAME, refreshToken, (int) REFRESH_TOKEN_VALID_TIME_OP_KEEP / 1000);
        } else {
            return AuthWebUtil.makeCookie(REFRESH_TOKEN_NAME, refreshToken, (int) REFRESH_TOKEN_VALID_TIME / 1000);
        }
    }
}
