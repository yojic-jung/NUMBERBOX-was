package com.kamcci.modules.auth.engine.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAuthenticationTokenTest {
    final String principal = "principal";
    final String credential = "credential";
    final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(principal, credential, null);

    @Test
    void getCredentials_성공() {
        assertThat(jwtAuthenticationToken.getCredentials()).isEqualTo(credential);
    }

    @Test
    void getPrincipal_성공() {
        assertThat(jwtAuthenticationToken.getPrincipal()).isEqualTo(principal);
    }

}