package com.kamcci.modules.auth.engine.config;

import com.kamcci.modules.auth.control.service.TokenResponseService;
import com.kamcci.modules.auth.engine.provider.JwtRequestAuthProvider;
import com.kamcci.modules.auth.engine.provider.LoginRequestAuthProvider;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import com.kamcci.modules.auth.mock.common.MockApplicationEventPublisher;
import com.kamcci.modules.auth.mock.handler.MockAuthenticationFailureHandler;
import com.kamcci.modules.auth.mock.handler.MockAuthenticationSuccessHandler;
import com.kamcci.modules.auth.mock.service.MockJwtRequestUserDetailService;
import com.kamcci.modules.auth.mock.service.MockLoginRequestUserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthJwtProperty;
import static com.kamcci.modules.auth.config.AuthConfigFixture.getAuthLoginUrlProperty;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SecurityBeanConfigTest {
    private final SecurityBeanConfig beanConfig = new SecurityBeanConfig(getAuthJwtProperty(),
            getAuthLoginUrlProperty());

    @Test
    void beanConfig_의존설정_성공() {
        AuthTokenUtil authTokenUtil = beanConfig.authTokenUtil();
        PasswordEncoder passwordEncoder = beanConfig.passwordEncoder();
        UserDetailsService userDetailsService =
                beanConfig.loginRequestUserService(new MockLoginRequestUserDetailService());
        TokenResponseService tokenResponseService =
                beanConfig.tokenResponseService(new MockApplicationEventPublisher());
        LoginRequestAuthProvider loginRequestAuthProvider = beanConfig.loginRequestAuthProvider(userDetailsService,
                passwordEncoder);
        JwtRequestAuthProvider jwtRequestAuthProvider = beanConfig.jwtRequestAuthProvider(userDetailsService,
                new MockJwtRequestUserDetailService(), authTokenUtil);
        AuthenticationManager authenticationManager = beanConfig.authenticationManager(loginRequestAuthProvider,
                jwtRequestAuthProvider);

        // then
        assertThat(passwordEncoder).isNotNull();
        assertThat(authTokenUtil).isNotNull();
        assertThat(tokenResponseService).isNotNull();
        assertThat(userDetailsService).isNotNull();
        assertThat(loginRequestAuthProvider).isNotNull();
        assertThat(jwtRequestAuthProvider).isNotNull();
        assertThat(authenticationManager).isNotNull();
        assertThat(beanConfig.loginRequestAuthenticationFilter(authenticationManager,
                new MockAuthenticationSuccessHandler(), new MockAuthenticationFailureHandler())).isNotNull();
        assertThat(beanConfig.jwtRequestAuthFilter(tokenResponseService, authenticationManager)).isNotNull();
    }
}