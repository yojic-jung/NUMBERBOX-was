package com.kamcci.modules.auth.stub;

import com.kamcci.modules.auth.engine.util.AuthTokenUtil;

import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.constant.MockAuthTestConstant.*;

public class MockAuthTokenUtil implements AuthTokenUtil {
    @Override
    public String reCreateAccessToken(String email, UUID userUniqId, List<String> roleList) {
        return null;
    }

    @Override
    public String reCreateAccessToken(String oldAccessToken) {
        return null;
    }

    @Override
    public String createRefreshToken(long validTime) {
        return null;
    }

    @Override
    public String reCreateRefreshToken(String oldRefreshToken) {
        return null;
    }

    @Override
    public String getEmail(String accessToken) {
        if(accessToken.equals(FAIL_TOKEN)) return FAIL_STRING;
        return "";
    }

    @Override
    public UUID getUserId(String accessToken) {
        if(accessToken.equals(FAIL_STRING)) return FAIL_MEMBER_ID;
        return UUID.fromString(accessToken);
    }

    @Override
    public long getValidTime(String token) {
        return 0;
    }

    @Override
    public List<String> getRoles(String accessToken) {
        return null;
    }

    @Override
    public void checkValidToken(String jwtToken, boolean checkExpire) {

    }

    @Override
    public boolean isExpiredToken(String jwtToken) {
        return !FAIL_STRING.equals(jwtToken);
    }
}
