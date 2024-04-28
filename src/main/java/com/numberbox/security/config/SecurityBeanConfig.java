package com.numberbox.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberbox.security.filter.JwtRequestAuthFilter;
import com.numberbox.security.filter.LoginRequestAuthFilter;
import com.numberbox.security.handler.JwtAuthFailureHandler;
import com.numberbox.security.provider.JwtRequestAuthProvider;
import com.numberbox.security.provider.JwtUtil;
import com.numberbox.security.provider.LoginRequestAuthProvider;
import com.numberbox.security.service.LoginRequestUserDetailService;
import com.numberbox.security.service.LoginRequestUserDetailServiceWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    @Bean
    public UserDetailsService userDetailsService(LoginRequestUserDetailService loginRequestUserService) {
        return new LoginRequestUserDetailServiceWrapper(loginRequestUserService);
    }


    @Bean
    public LoginRequestAuthProvider loginRequestAuthProvider(UserDetailsService userDetailsService,
                                                             PasswordEncoder passwordEncoder) {
        return new LoginRequestAuthProvider(userDetailsService, passwordEncoder);
    }

    @Bean
    public JwtRequestAuthProvider jwtRequestAuthProvider(JwtUtil JwtUtil, ObjectMapper objectMapper) {
        return new JwtRequestAuthProvider(JwtUtil, objectMapper);
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
            JwtUtil jwtUtil,
            JwtAuthFailureHandler jwtAuthFailureHandler
    ) {
        return new JwtRequestAuthFilter(
                authenticationManager,
                jwtUtil,
                jwtAuthFailureHandler
        );
    }

}
