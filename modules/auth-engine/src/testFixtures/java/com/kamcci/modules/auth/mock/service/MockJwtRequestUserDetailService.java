package com.kamcci.modules.auth.mock.service;

import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;

import java.util.UUID;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.FAIL_MEMBER_ID;

public class MockJwtRequestUserDetailService implements JwtRequestUserDetailService {
    @Override
    public UUID loadUserIdByRefreshToken(String token) {
        return UUID.fromString(token);
    }

    @Override
    public boolean canReCreateRefreshToken(UUID userId) {
        return userId != FAIL_MEMBER_ID;
    }
}
