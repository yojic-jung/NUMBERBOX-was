package com.kamcci.modules.auth.engine.config;

import com.kamcci.modules.auth.engine.filter.LoginRequestAuthFilter;
import com.kamcci.modules.auth.engine.handler.CustomAuthenticationEntryPoint;
import com.kamcci.modules.auth.stub.common.MockApplicationEventPublisher;
import com.kamcci.modules.auth.stub.common.MockAuthenticationManager;
import com.kamcci.modules.auth.stub.common.MockAuthenticationProvider;
import com.kamcci.modules.auth.stub.common.MockObjectPostProcessor;
import com.kamcci.modules.auth.stub.filter.MockJwtRequestAuthFilter;
import com.kamcci.modules.auth.stub.handler.MockAuthenticationFailureHandler;
import com.kamcci.modules.auth.stub.handler.MockAuthenticationSuccessHandler;
import com.kamcci.modules.auth.stub.service.MockUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.session.ChangeSessionIdAuthenticationStrategy;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.HashMap;
import java.util.Map;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthLoginUrlProperty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SecurityConfigTest {
    private SecurityConfig securityConfig;

    @BeforeEach
    void setUpBefore() {
        securityConfig = new SecurityConfig(getAuthLoginUrlProperty(), new MockApplicationEventPublisher());
    }

    @Test
    void webSecurityCustomizer_빈등록_설정_성공() {
        assertThat(securityConfig.webSecurityCustomizer()).isInstanceOf(WebSecurityCustomizer.class);
    }

    @Test
    void filterChain_빈등록_설정_성공() throws Exception {
        // givne
        HttpSecurity httpSecurity = getHttpSecurity();
        LoginRequestAuthFilter loginRequestAuthFilter = new LoginRequestAuthFilter("processUrl",
                new MockAuthenticationManager(), new MockAuthenticationSuccessHandler(),
                new MockAuthenticationFailureHandler());

        // when
        SecurityFilterChain filterChain = securityConfig.filterChain(httpSecurity, new MockUserDetailsService(),
                new CustomAuthenticationEntryPoint(), loginRequestAuthFilter, new MockJwtRequestAuthFilter());

        // then
        assertThat(filterChain).isInstanceOf(SecurityFilterChain.class);
    }

    // 테스트용 HttpSecurity 의존 설정
    private HttpSecurity getHttpSecurity() throws Exception {
        MockObjectPostProcessor mockObjectPostProcessor = new MockObjectPostProcessor();
        AuthenticationManagerBuilder authenticationManagerBuilder =
                new AuthenticationManagerBuilder(mockObjectPostProcessor);
        authenticationManagerBuilder.authenticationProvider(new MockAuthenticationProvider());
        authenticationManagerBuilder.parentAuthenticationManager(new MockAuthenticationManager());
        Map<Class<?>, Object> sharedObjects = new HashMap<>();
        GenericApplicationContext context = new GenericApplicationContext();
        context.refresh();
        sharedObjects.put(ApplicationContext.class, context);

        // filterChain 객체 생성에서 context가 자동 주입 시켜 주는 객체 직접 주입
        HttpSecurity security = new HttpSecurity(mockObjectPostProcessor, authenticationManagerBuilder, sharedObjects);
        security.sessionManagement(session -> {
            session.sessionAuthenticationStrategy(new ChangeSessionIdAuthenticationStrategy());
        });
        return security;
    }

    @Test
    void corsConfigurationSource_빈등록_설정_성공() {
        assertThat(securityConfig.corsConfigurationSource()).isInstanceOf(CorsConfigurationSource.class);
    }
}