package com.numberbox.security.handler;

import com.numberbox.security.dto.AuthResponse;
import com.numberbox.security.dto.LoginSuccessEvent;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.numberbox.security.provider.JwtUtil.REFRESH_TOKEN_VALID_TIME;
import static com.numberbox.security.provider.JwtUtil.REFRESH_TOKEN_VALID_TIME_DEFAULT;
import static com.numberbox.security.util.SecurityUtil.makeCookie;
import static com.numberbox.security.util.SecurityUtil.responseOK;

/**
 * Def. 로그인 인증 성공 후처리
 */
@Primary
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil;
    private final ApplicationEventPublisher eventPublisher;

    public LoginSuccessHandler(JwtUtil jwtUtil, ApplicationEventPublisher eventPublisher) {
        this.jwtUtil = jwtUtil;
        this.eventPublisher = eventPublisher;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        final String username = (String) authentication.getPrincipal();
        final UUID userId = (UUID) authentication.getDetails();

        // 권한 가져오기
        final List<String> roleList = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // accessToken(사용자 식별값, 권한) 및 refreshToken 발행
        final String accessToken = jwtUtil.createAccessToken(username, userId, roleList);
        final String refreshToken = jwtUtil.createRefreshToken();

        // 로그인 성공 이벤트 발행
        final String remainedRefreshToken = jwtUtil.resolveRefreshToken(request);
        final LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent(userId, refreshToken, remainedRefreshToken);
        eventPublisher.publishEvent(loginSuccessEvent);

        // 응답(토큰, 권한 포함)
        response.setHeader("access-token", accessToken);
        response.setHeader("role", roleList.toString());
        response.addCookie(makeRefreshTokenCookie(request, refreshToken));
        responseOK(response, false, AuthResponse.OK.message);
    }

    /**
     * 리프레시 토큰 쿠키 생성
     */
    private Cookie makeRefreshTokenCookie(HttpServletRequest request, String refreshToken) {
        // 리프레시 토큰 유효기간 설정
        final String loginState = request.getParameter("loginState");

        // 클라이언트가 로그인 상태 유지 요청한 경우
        if (loginState != null && loginState.equals("keep")) {
            return makeCookie("refresh-token", refreshToken, (int) REFRESH_TOKEN_VALID_TIME / 1000);
        } else {
            return makeCookie("refresh-token", refreshToken, (int) REFRESH_TOKEN_VALID_TIME_DEFAULT / 1000);
        }
    }
}
