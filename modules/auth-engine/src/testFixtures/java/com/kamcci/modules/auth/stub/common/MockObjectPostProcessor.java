package com.kamcci.modules.auth.stub.common;

import com.kamcci.modules.auth.engine.handler.CustomAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.access.intercept.RequestMatcherDelegatingAuthorizationManager;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessEventPublishingLogoutHandler;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.security.web.authentication.session.CompositeSessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Def. Security config 테스트를 위한 stub
 * Desc. Security 기본 동작에서 의존객체 필로한 타입별 생성
 */
public class MockObjectPostProcessor implements ObjectPostProcessor<Object> {
    @Override
    public <O> O postProcess(O object) {
        if(object instanceof CompositeSessionAuthenticationStrategy)
            return (O) new ChangeSessionIdAuthenticationStrategy();
        else if(object instanceof DaoAuthenticationProvider) return (O) new DaoAuthenticationProvider();
        else if(object instanceof RequestMatcherDelegatingAuthorizationManager) {
            AuthorizationManager<RequestAuthorizationContext> manager = new AuthenticatedAuthorizationManager<>();
            return (O) RequestMatcherDelegatingAuthorizationManager.builder()
                    .add(new AntPathRequestMatcher("/aaa"), manager).build();
        } else if(object instanceof AuthorizationFilter) {
            AuthorizationManager<HttpServletRequest> authorizationManager = new AuthenticatedAuthorizationManager<>();
            return (O) new AuthorizationFilter(authorizationManager);
        } else if(object instanceof ExceptionTranslationFilter) {
            return (O) new ExceptionTranslationFilter(new CustomAuthenticationEntryPoint(),
                    new HttpSessionRequestCache());
        } else if(object instanceof LogoutSuccessEventPublishingLogoutHandler) {
            return (O) new LogoutSuccessEventPublishingLogoutHandler();
        } else if(object instanceof LogoutFilter) {
            return (O) new LogoutFilter("/sadf", new LogoutSuccessEventPublishingLogoutHandler());
        } else if(object instanceof ProviderManager) {
            List<AuthenticationProvider> list = new ArrayList<>();
            list.add(new MockAuthenticationProvider());
            return (O) new ProviderManager(list, new MockAuthenticationManager());
        } else return null;
    }

}
