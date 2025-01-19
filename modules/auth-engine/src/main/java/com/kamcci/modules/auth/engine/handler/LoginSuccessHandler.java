package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.control.dto.AuthResponse;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.ROLE_PREFIX;

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
        final Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        final UUID userId = (UUID) details.get("userId");

        // 권한 가져오기
        final List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .map(authority -> authority.replace(ROLE_PREFIX, "")).collect(Collectors.toList());

        // 응답(토큰, 권한 포함)
        tokenResponseService.responseAuthToken(username, userId, roleList);
        AuthWebUtil.responseOK(response, AuthResponse.LOGIN_OK.message);
    }
}
