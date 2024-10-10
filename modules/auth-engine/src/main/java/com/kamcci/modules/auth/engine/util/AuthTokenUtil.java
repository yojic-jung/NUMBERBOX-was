package com.kamcci.modules.auth.engine.util;

import java.util.List;
import java.util.UUID;

// todo 클래스명 변경 util 없애기
public interface AuthTokenUtil {
    String createAccessToken(String email, UUID userUniqId, List<String> roleList);

    String createAccessToken(String oldAccessToken);

    String createRefreshToken();

    String getEmail(String token);

    UUID getUserUniqId(String token);

    List<String> getRoles(String token);

    void checkValidToken(String jwtToken, boolean checkExpiration);
}
