package com.numberbox.auth.control.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface AuthTokenService {
    String extractTokenFromRequestHeader(String tokenName);

    String extractTokenFromCookie(String tokenName);

    String createAccessToken(String email, UUID userUniqId, List<String> roleList);

    String createRefreshToken();

    String getEmail(String token);

    UUID getUserUniqId(String token);

    void throwExceptionIfInvalidToken(String jwtToken);

    void throwExceptionIfInvalidToken(String jwtToken, boolean exceptExpiration);
}
