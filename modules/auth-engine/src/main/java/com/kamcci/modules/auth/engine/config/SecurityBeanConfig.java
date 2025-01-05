package com.kamcci.modules.auth.engine.config;

import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService;
import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.engine.filter.JwtRequestAuthFilter;
import com.kamcci.modules.auth.engine.filter.LoginRequestAuthFilter;
import com.kamcci.modules.auth.engine.provider.JwtRequestAuthProvider;
import com.kamcci.modules.auth.engine.provider.LoginRequestAuthProvider;
import com.kamcci.modules.auth.engine.service.JwtResponseHeaderCookieService;
import com.kamcci.modules.auth.engine.service.LoginRequestUserDetailServiceWrapper;
import com.kamcci.modules.auth.engine.util.AuthJwtUtil;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties(value = {AuthJwtProperty.class, AuthLoginUrlProperty.class})
public class SecurityBeanConfig {
    private final AuthJwtProperty authJwtProperty;
    private final AuthLoginUrlProperty authLoginUrlProperty;

    public SecurityBeanConfig(AuthJwtProperty authJwtProperty, AuthLoginUrlProperty authLoginUrlProperty) {
        this.authJwtProperty = authJwtProperty;
        this.authLoginUrlProperty = authLoginUrlProperty;
    }

    /**
     * 비밀번호 인코더
     */
    @Primary
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenUtil authTokenUtil() {
        return new AuthJwtUtil(authJwtProperty);
    }

    @Bean
    public TokenResponseService tokenResponseService(ApplicationEventPublisher eventPublisher) {
        return new JwtResponseHeaderCookieService(authTokenUtil(), eventPublisher, authJwtProperty);
    }

    /**
     * 로그인 인증 요청시 서버측 사용자 인증 정보를 가져옴
     */
    @Primary
    @Bean
    public UserDetailsService loginRequestUserService(LoginRequestUserDetailService loginRequestUserService) {
        return new LoginRequestUserDetailServiceWrapper(loginRequestUserService);
    }

    /**
     * 로그인 인증 요청시 인증 처리 담당 provider
     */
    @Bean
    public LoginRequestAuthProvider loginRequestAuthProvider(UserDetailsService userDetailsService,
                                                             PasswordEncoder passwordEncoder) {
        return new LoginRequestAuthProvider(userDetailsService, passwordEncoder);
    }

    /**
     * jwt 인증 요청시 인증 처리 담당 provider
     */
    @Bean
    public JwtRequestAuthProvider jwtRequestAuthProvider(UserDetailsService userDetailsService,
                                                         JwtRequestUserDetailService jwtRequestUserDetailService,
                                                         AuthTokenUtil authTokenUtil) {
        return new JwtRequestAuthProvider(userDetailsService, jwtRequestUserDetailService, authTokenUtil);
    }

    /**
     * 알맞은 인증 처리 담당 provider에게 인증 처리 할당
     */
    @Bean
    public AuthenticationManager authenticationManager(LoginRequestAuthProvider loginRequestAuthProvider,
                                                       JwtRequestAuthProvider jwtRequestAuthProvider) {
        List<AuthenticationProvider> list = new ArrayList<>();
        list.add(loginRequestAuthProvider);
        list.add(jwtRequestAuthProvider);
        return new ProviderManager(list);
    }

    /**
     * 로그인 인증 요청시 사용되는 인증 필터
     */
    @Bean
    public LoginRequestAuthFilter loginRequestAuthenticationFilter(AuthenticationManager authenticationManager,
                                                                   AuthenticationSuccessHandler authenticationSuccessHandler,
                                                                   AuthenticationFailureHandler authenticationFailureHandler) {
        return new LoginRequestAuthFilter(authLoginUrlProperty.process(), authenticationManager,
                authenticationSuccessHandler, authenticationFailureHandler);
    }

    /**
     * jwt 인증 요청시 사용되는 인증 필터
     */
    @Bean
    public JwtRequestAuthFilter jwtRequestAuthFilter(TokenResponseService tokenResponseService,
                                                     AuthenticationManager authenticationManager) {
        return new JwtRequestAuthFilter(authenticationManager, tokenResponseService);
    }

}
