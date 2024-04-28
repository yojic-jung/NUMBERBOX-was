package com.numberbox.security.handler;

import com.numberbox.security.dto.LoginSuccessEvent;
import com.numberbox.security.provider.JwtUtil;
import com.numberbox.security.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        String username = (String) authentication.getPrincipal();
        UUID userId = (UUID) authentication.getDetails();

        // todo 프론트단에서 권한 체크
        List<String> roleList = new ArrayList<>();
        for (GrantedAuthority authority : authentication.getAuthorities()) {
            roleList.add(authority.getAuthority());
        }

        // todo jwtUtil 의존도 고민
        // todo 사용자 로그 찍어야함??
        // accessToken 및 refreshToken 발행
        String accessToken = jwtUtil.createAccessToken(username, userId, roleList);
        String refreshToken = jwtUtil.createRefreshToken(username, userId);

        // 로그인 성공 이벤트 발행
        String remainedRefreshToken = jwtUtil.resolveRefreshToken(request);
        LoginSuccessEvent loginSuccessEvent = new LoginSuccessEvent(userId, refreshToken, remainedRefreshToken);
        eventPublisher.publishEvent(loginSuccessEvent);

        System.out.println("3333");
        SecurityUtil.respondOkWithToken(request, response, accessToken,
                refreshToken, roleList, "로그인 성공 하였습니다.");
    }
}
