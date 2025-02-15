package com.kamcci.modules.auth.engine.util;

import com.kamcci.modules.auth.stub.util.MockPasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordEncoderWrapperTest {
    private MockPasswordEncoder passwordEncoder;
    private PasswordEncoderWrapper authPasswordEncoderWrapper;

    @BeforeEach
    void 테스트_대상_초기화() {
        passwordEncoder = new MockPasswordEncoder();
        authPasswordEncoderWrapper = new PasswordEncoderWrapper(passwordEncoder);
    }

    @Test
    void matches_동작_성공() {
        authPasswordEncoderWrapper.matches("", "");

        // then
        assertThat(passwordEncoder.executeCnt).isOne();
    }

    @Test
    void encode_동작_성공() {
        authPasswordEncoderWrapper.encode("");

        // then
        assertThat(passwordEncoder.executeCnt).isOne();
    }
}