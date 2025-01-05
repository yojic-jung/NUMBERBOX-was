package com.kamcci.modules.auth.engine.util;

import com.kamcci.modules.auth.engine.config.AuthJwtProperty;
import com.kamcci.modules.auth.engine.exception.JwtInvalidException;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.kamcci.modules.auth.control.config.AuthConstantConfig.ROLE_NAME;

@Component
public class AuthJwtUtil implements AuthTokenUtil {
    private final AuthJwtProperty authJwtProperty;

    public AuthJwtUtil(AuthJwtProperty authJwtProperty) {
        this.authJwtProperty = authJwtProperty;
    }

    @Override
    public String createAccessToken(String email, UUID userUniqId, List<String> roleList) {
        Claims claims = Jwts.claims();
        claims.put(authJwtProperty.email(), email);
        claims.put(authJwtProperty.id(), userUniqId);
        claims.put(ROLE_NAME, roleList);
        claims.put(authJwtProperty.domain(), true);

        Date now = new Date();
        Date expiration = new Date(now.getTime() + authJwtProperty.accessToken().validTime());
        return Jwts.builder().setClaims(claims).setIssuer(authJwtProperty.issuer())
                .setSubject(authJwtProperty.accessToken().subject()).setAudience(authJwtProperty.audience())
                .setIssuedAt(now).setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, authJwtProperty.secretKey()).compact();
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
        claims.put(authJwtProperty.domain(), true);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + authJwtProperty.refreshToken().validTime());
        return Jwts.builder().setClaims(claims).setIssuer(authJwtProperty.issuer())
                .setSubject(authJwtProperty.refreshToken().subject()).setAudience(authJwtProperty.audience())
                .setExpiration(expiration).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, authJwtProperty.secretKey()).compact();
    }

    @Override
    public String getEmail(String token) {
        try {
            // 토큰 파싱 시도
            return Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(token).getBody()
                    .get(authJwtProperty.email(), String.class);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            return e.getClaims().get(authJwtProperty.email(), String.class);
        }
    }

    @Override
    public UUID getUserUniqId(String token) {
        try {
            String uuid = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(token).getBody()
                    .get(authJwtProperty.id(), String.class);
            return UUID.fromString(uuid);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            String uuid = e.getClaims().get(authJwtProperty.id(), String.class);
            return UUID.fromString(uuid);
        }
    }

    @Override
    public List<String> getRoles(String token) {
        try {
            return (List<String>) Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(token)
                    .getBody().get(ROLE_NAME, Object.class);
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
            Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(jwtToken);
        } catch(ExpiredJwtException e) {
            if(checkExpiration) throw new TokenExpirationException();
        } catch(Exception e) {
            throw new JwtInvalidException();
        }
    }
}
