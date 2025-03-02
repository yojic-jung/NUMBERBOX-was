package com.kamcci.modules.auth.engine.dto;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class JwtAuthenticationTokenTest {
    private final String principal = "principal";
    private final String credential = "credential";
    private final JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(principal, credential,
            null);

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
        // given - 토큰 인증 정보 같게 설정
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("", "", new ArrayList<>());
        JwtAuthenticationToken compareToken = new JwtAuthenticationToken("", "", new ArrayList<>());

        // then
        assertThat(jwtToken).isEqualTo(compareToken);
    }

    @Test
    void equals_실패() {
        // given
        boolean isTarget = true; // 검증/비교 대상 여부
        List<JwtAuthenticationToken> jwtTokenList = getTcTokenList(isTarget);
        List<JwtAuthenticationToken> compareTokenList = getTcTokenList(!isTarget);

        for(int i = 0; i < 3; i++) {
            // then
            assertThat(jwtTokenList.get(i)).isNotEqualTo(compareTokenList.get(i));
        }
    }

    // JwtAuthenticationToken tc 데이터 반환
    private List<JwtAuthenticationToken> getTcTokenList(Boolean isTarget) {
        if(isTarget) {
            // 검증 대상
            List<JwtAuthenticationToken> jwtTokenList = new ArrayList<>();
            jwtTokenList.add(new JwtAuthenticationToken("", "1", new ArrayList<>()));
            jwtTokenList.add(new JwtAuthenticationToken("1", "", new ArrayList<>()));
            jwtTokenList.add(new JwtAuthenticationToken("1", "1", new ArrayList<>()));
            return jwtTokenList;
        } else {
            // 비교 대상
            List<JwtAuthenticationToken> compareTokenList = new ArrayList<>();
            compareTokenList.add(new JwtAuthenticationToken("1", "1", new ArrayList<>()));
            compareTokenList.add(new JwtAuthenticationToken("1", "1", new ArrayList<>()));
            compareTokenList.add(new JwtAuthenticationToken("", "1", new ArrayList<>()));
            return compareTokenList;
        }
    }

    @Test
    void equals_실패_토큰_아님() {
        // given - 토큰 타입 다르게 설정
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("1", "1", new ArrayList<>());
        String token = "";

        // then
        assertThat(jwtToken).isNotEqualTo(token);
    }

    @Test
    void hashcode_성공() {
        // given - 토큰 정보 같게 설정
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken("", "", new ArrayList<>());
        JwtAuthenticationToken compareToken = new JwtAuthenticationToken("", "", new ArrayList<>());

        // then
        assertThat(jwtToken).hasSameHashCodeAs(compareToken);
    }
}