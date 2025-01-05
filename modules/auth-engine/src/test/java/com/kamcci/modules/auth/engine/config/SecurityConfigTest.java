package com.kamcci.modules.auth.engine.config;

import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("auth-test")
@SpringBootTest
class SecurityConfigTest {
    @Autowired
    SecurityConfig securityConfig;
    @MockBean
    LoginRequestUserDetailService loginRequestUserDetailService;
    @MockBean
    JwtRequestUserDetailService jwtRequestUserDetailService;

    @Test
    void config() {
        assertThat(securityConfig.webSecurityCustomizer()).isNotNull();
        assertThat(securityConfig.corsConfigurationSource()).isNotNull();
    }
}