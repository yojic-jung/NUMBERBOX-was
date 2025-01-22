package com.numberbox.auth.engine.util;

import java.util.List;
import java.util.UUID;

// todo 클래스명 변경 util 없애기
public interface AuthTokenUtil {
    String createAccessToken(String email, UUID userUniqId, List<String> roleList);

    String createRefreshToken();

    String getEmail(String token);

    String getUserUniqId(String token);

    void throwExceptionIfInvalidToken(String jwtToken);

    void throwExceptionIfInvalidToken(String jwtToken, boolean exceptExpiration);
}
