package com.kamcci.modules.auth.engine.handler;

import com.kamcci.modules.auth.control.dto.AuthResponse;
import com.kamcci.modules.auth.control.dto.LogoutSuccessEvent;
import com.kamcci.modules.auth.engine.util.AuthWebUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.util.WebUtils;

public class JwtLogoutSuccessHandler implements LogoutSuccessHandler {
    private final ApplicationEventPublisher eventPublisher;

    public JwtLogoutSuccessHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) {
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if(cookie != null) {
            String jwtToken = cookie.getValue();
            eventPublisher.publishEvent(new LogoutSuccessEvent(jwtToken));
        }
        AuthWebUtil.responseOK(response, AuthResponse.LOGOUT_OK.message);
    }
}
