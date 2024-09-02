package com.numberbox.modules.auth.engine.config;

import com.numberbox.modules.auth.engine.util.AuthTokenUtil;
import com.numberbox.modules.auth.control.service.JwtRequestUserDetailService;
import com.numberbox.modules.auth.control.service.LoginRequestUserDetailService;
import com.numberbox.modules.auth.engine.filter.JwtRequestAuthFilter;
import com.numberbox.modules.auth.engine.filter.LoginRequestAuthFilter;
import com.numberbox.modules.auth.engine.provider.JwtRequestAuthProvider;
import com.numberbox.modules.auth.engine.provider.LoginRequestAuthProvider;
import com.numberbox.modules.auth.engine.service.JwtRequestUserDetailServiceWrapper;
import com.numberbox.modules.auth.engine.service.LoginRequestUserDetailServiceWrapper;
import com.numberbox.modules.auth.engine.service.UserTokenDetailService;
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
public class SecurityBeanConfig {
    /**
     * 비밀번호 인코더
     */
    @Primary
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
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
     * jwt 인증시 서버측 사용자 인증 정보를 가져옴
     */
    @Bean
    public UserTokenDetailService jwtRequestUserDetailService(LoginRequestUserDetailService loginRequestUserService,
                                                              JwtRequestUserDetailService jwtRequestUserDetailService) {
        return new JwtRequestUserDetailServiceWrapper(loginRequestUserService, jwtRequestUserDetailService);
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
    public JwtRequestAuthProvider jwtRequestAuthProvider(UserTokenDetailService userTokenDetailService,
                                                         AuthTokenUtil authTokenUtil) {
        return new JwtRequestAuthProvider(userTokenDetailService, authTokenUtil);
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
    public LoginRequestAuthFilter loginRequestAuthenticationFilter(
            AuthenticationManager authenticationManager,
            AuthenticationSuccessHandler authenticationSuccessHandler,
            AuthenticationFailureHandler authenticationFailureHandler
    ) {
        return new LoginRequestAuthFilter(
                authenticationManager,
                authenticationSuccessHandler,
                authenticationFailureHandler
        );
    }

    /**
     * jwt 인증 요청시 사용되는 인증 필터
     */
    @Bean
    public JwtRequestAuthFilter jwtRequestAuthFilter(
            AuthenticationManager authenticationManager
    ) {
        return new JwtRequestAuthFilter(authenticationManager);
    }

}
