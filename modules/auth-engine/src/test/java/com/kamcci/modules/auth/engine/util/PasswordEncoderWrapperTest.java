package com.kamcci.modules.auth.engine.util;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class PasswordEncoderWrapperTest {
    private final PasswordEncoder passwordEncoder = mock();
    private final PasswordEncoderWrapper authPasswordEncoderWrapper = new PasswordEncoderWrapper(passwordEncoder);

    @Test
    void matches_동작_성공() {
        authPasswordEncoderWrapper.matches("", "");

        // then
        verify(passwordEncoder).matches("", "");
    }

    @Test
    void encode_동작_성공() {
        authPasswordEncoderWrapper.encode("");

        // then
        verify(passwordEncoder).encode("");
    }
}