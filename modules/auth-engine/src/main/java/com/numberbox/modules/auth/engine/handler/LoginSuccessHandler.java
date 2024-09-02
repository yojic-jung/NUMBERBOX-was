package com.numberbox.modules.auth.engine.handler;

import com.numberbox.modules.auth.control.dto.AuthResponse;
import com.numberbox.modules.auth.control.service.TokenResponseService;
import com.numberbox.modules.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Def. 로그인 인증 성공 후처리
 */
@Primary
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final TokenResponseService tokenResponseService;

    public LoginSuccessHandler(TokenResponseService tokenResponseService) {
        this.tokenResponseService = tokenResponseService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        final String username = (String) authentication.getPrincipal();
        final UUID userId = (UUID) authentication.getDetails();

        // 권한 가져오기
        final List<String> roleList = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 응답(토큰, 권한 포함)
        tokenResponseService.createAndSetTokenToResponse(username, userId, roleList);
        AuthWebUtil.responseOK(response, false, AuthResponse.OK.message);
    }
}
