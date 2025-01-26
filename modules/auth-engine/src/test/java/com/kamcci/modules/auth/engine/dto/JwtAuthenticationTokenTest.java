package com.kamcci.modules.auth.engine.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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

    @Test
    void equals_성공() {
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("", "", new ArrayList<>());
        JwtAuthenticationToken compareToken = new JwtAuthenticationToken("", "", new ArrayList<>());

        assertThat(jwtToken).isEqualTo(compareToken);
    }

    @Test
    void equals_실패() {
        for(int i = 0; i < 3; i++) {
            JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(i == 0 ? "" : "1", i == 1 ? "" : "1",
                    new ArrayList<>());
            JwtAuthenticationToken compareToken = new JwtAuthenticationToken(i == 2 ? "" : "1", "1", new ArrayList<>());

            assertThat(jwtToken).isNotEqualTo(compareToken);
        }
    }

    @Test
    void equals_실패_토큰_아님() {
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("1", "1", new ArrayList<>());
        String token = "";
        assertThat(jwtToken).isNotEqualTo(token);
    }

    @Test
    void hashcode_성공() {
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("", "", new ArrayList<>());
        JwtAuthenticationToken compareToken = new JwtAuthenticationToken("", "", new ArrayList<>());

        assertThat(jwtToken).hasSameHashCodeAs(compareToken);
    }
}