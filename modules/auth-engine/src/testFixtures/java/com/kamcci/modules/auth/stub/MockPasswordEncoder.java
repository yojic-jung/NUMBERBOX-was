package com.kamcci.modules.auth.stub;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MockPasswordEncoder implements PasswordEncoder {
    // 실행 횟수
    public int executeCnt = 0;

    @Override
    public String encode(CharSequence rawPassword) {
        executeCnt++;
        return "";
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        executeCnt++;
        return false;
    }
}
