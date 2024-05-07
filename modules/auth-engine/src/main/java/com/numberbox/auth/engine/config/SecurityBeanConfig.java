package com.numberbox.auth.engine.config;

import com.numberbox.auth.control.service.AuthTokenService;
import com.numberbox.auth.control.service.JwtRequestUserDetailService;
import com.numberbox.auth.control.service.LoginRequestUserDetailService;
import com.numberbox.auth.engine.filter.JwtRequestAuthFilter;
import com.numberbox.auth.engine.filter.LoginRequestAuthFilter;
import com.numberbox.auth.engine.provider.JwtRequestAuthProvider;
import com.numberbox.auth.engine.provider.LoginRequestAuthProvider;
import com.numberbox.auth.engine.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SecurityBeanConfig {
    @Primary
    @Bean
    public UserDetailsService loginRequestUserService(LoginRequestUserDetailService loginRequestUserService) {
        return new LoginRequestUserDetailServiceWrapper(loginRequestUserService);
    }

    @Bean
    public UserTokenDetailService jwtRequestUserDetailService(LoginRequestUserDetailService loginRequestUserService,
                                                              JwtRequestUserDetailService jwtRequestUserDetailService) {
        return new JwtRequestUserDetailServiceWrapper(loginRequestUserService, jwtRequestUserDetailService);
    }

    @Bean
    public LoginRequestAuthProvider loginRequestAuthProvider(UserDetailsService userDetailsService,
                                                             PasswordEncoder passwordEncoder) {
        return new LoginRequestAuthProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public JwtRequestAuthProvider jwtRequestAuthProvider(UserTokenDetailService userTokenDetailService,
                                                         AuthTokenService authTokenService) {
        return new JwtRequestAuthProvider(userTokenDetailService, authTokenService);
    }

    @Bean
    public AuthenticationManager authenticationManager(LoginRequestAuthProvider loginRequestAuthProvider,
                                                       JwtRequestAuthProvider jwtRequestAuthProvider) {
        List<AuthenticationProvider> list = new ArrayList<>();
        list.add(loginRequestAuthProvider);
        list.add(jwtRequestAuthProvider);
        return new ProviderManager(list);
    }

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

    @Bean
    public JwtRequestAuthFilter jwtRequestAuthFilter(
            AuthenticationManager authenticationManager,
            AuthTokenService authTokenService
    ) {
        return new JwtRequestAuthFilter(authenticationManager, authTokenService);
    }

}
