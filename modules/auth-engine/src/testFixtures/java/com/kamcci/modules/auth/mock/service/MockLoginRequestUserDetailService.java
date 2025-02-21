package com.kamcci.modules.auth.mock.service;

import com.kamcci.modules.auth.control.dto.AuthUserInfo;
import com.kamcci.modules.auth.control.service.LoginRequestUserDetailService;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.NULL_USER;
import static com.kamcci.modules.auth.sample.AuthUserSampleData.getAuthUserInfo;

public class MockLoginRequestUserDetailService implements LoginRequestUserDetailService {
    @Override
    public AuthUserInfo loadUserByUsername(String username) {
        if(NULL_USER.equals(username)) return null;
        return getAuthUserInfo();
    }
}
