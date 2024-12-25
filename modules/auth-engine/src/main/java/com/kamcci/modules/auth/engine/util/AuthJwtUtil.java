package com.kamcci.modules.auth.engine.util;

import com.kamcci.modules.auth.control.config.AuthConstantConfig;
import com.kamcci.modules.auth.engine.exception.JwtInvalidException;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.ROLE_NAME;

@Component
public class AuthJwtUtil implements AuthTokenUtil {
    private static final String EMAIL_KEY = "email";
    private static final String USER_UNIQ_ID_KEY = "userUniqId";
    private static final String DOMAIN = "nsoohak.com";
    private static final String ISSUER = "nsoohak";
    private static final String ACCESS_TOKEN_SUBJECT = "nsoohakAccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "nsoohakRefreshToken";
    private static final String AUDIENCE = "user";
    private String secretKey;

    @Value("${numberbox.jwtSecretKey}")
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String createAccessToken(String email, UUID userUniqId, List<String> roleList) {
        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, email);
        claims.put(USER_UNIQ_ID_KEY, userUniqId);
        claims.put(ROLE_NAME, roleList);
        claims.put(DOMAIN, true);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + AuthConstantConfig.ACCESS_TOKEN_VALID_TIME);
        return Jwts.builder().setClaims(claims).setIssuer(ISSUER).setSubject(ACCESS_TOKEN_SUBJECT).setAudience(AUDIENCE)
                .setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    @Override
    public String createAccessToken(String oldAccessToken) {
        String email = getEmail(oldAccessToken);
        UUID uuid = getUserUniqId(oldAccessToken);
        List<String> role = getRoles(oldAccessToken);
        return createAccessToken(email, uuid, role);
    }

    @Override
    public String createRefreshToken() {
        Claims claims = Jwts.claims();
        claims.put(DOMAIN, true);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + AuthConstantConfig.REFRESH_TOKEN_VALID_TIME);
        return Jwts.builder().setClaims(claims).setIssuer(ISSUER).setSubject(REFRESH_TOKEN_SUBJECT)
                .setAudience(AUDIENCE).setExpiration(expiration).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    @Override
    public String getEmail(String token) {
        try {
            // 토큰 파싱 시도
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(EMAIL_KEY, String.class);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            return e.getClaims().get(EMAIL_KEY, String.class);
        }
    }

    @Override
    public UUID getUserUniqId(String token) {
        try {
            String uuid = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                    .get(USER_UNIQ_ID_KEY, String.class);
            return UUID.fromString(uuid);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            String uuid = e.getClaims().get(USER_UNIQ_ID_KEY, String.class);
            return UUID.fromString(uuid);
        }
    }

    @Override
    public List<String> getRoles(String token) {
        try {
            return (List<String>) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody()
                    .get(ROLE_NAME, Object.class);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            return (List<String>) e.getClaims().get(ROLE_NAME, Object.class);
        }
    }

    /**
     * 토큰 유효성 검사(만료 여부 검사 지정 가능)
     */
    @Override
    public void checkValidToken(String jwtToken, boolean checkExpiration) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        } catch(ExpiredJwtException e) {
            if(checkExpiration) throw new TokenExpirationException();
        } catch(Exception e) {
            throw new JwtInvalidException();
        }
    }
}
