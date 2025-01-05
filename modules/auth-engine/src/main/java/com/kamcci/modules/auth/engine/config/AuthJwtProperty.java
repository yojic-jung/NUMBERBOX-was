package com.kamcci.modules.auth.engine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kamcci.auth.jwt", ignoreUnknownFields = false, ignoreInvalidFields = true)
public record AuthJwtProperty(String secretKey, String email, String id, String domain, String issuer, String audience,
                              AccessToken accessToken, RefreshToken refreshToken) {
    public record AccessToken(String subject, long validTime) { }

    public record RefreshToken(String subject, long validTime, long keepValidTime) { }

}
