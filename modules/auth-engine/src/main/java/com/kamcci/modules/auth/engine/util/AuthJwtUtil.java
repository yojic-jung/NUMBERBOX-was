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
    public String reCreateAccessToken(String email, UUID userUniqId, List<String> roleList) {
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
    public String reCreateAccessToken(String oldAccessToken) {
        String email = getEmail(oldAccessToken);
        UUID uuid = getUserId(oldAccessToken);
        List<String> role = getRoles(oldAccessToken);
        return reCreateAccessToken(email, uuid, role);
    }

    @Override
    public String createRefreshToken(long validTime) {
        Claims claims = Jwts.claims();
        claims.put(authJwtProperty.domain(), true);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + validTime);
        return Jwts.builder().setClaims(claims).setIssuer(authJwtProperty.issuer())
                .setSubject(authJwtProperty.refreshToken().subject()).setAudience(authJwtProperty.audience())
                .setExpiration(expiration).setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, authJwtProperty.secretKey()).compact();
    }

    @Override
    public String reCreateRefreshToken(String oldRefreshToken) {
        long validTime = getValidTime(oldRefreshToken);
        return createRefreshToken(validTime);
    }

    @Override
    public String getEmail(String accessToken) {
        try {
            // 토큰 파싱 시도
            return Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(accessToken).getBody()
                    .get(authJwtProperty.email(), String.class);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            return e.getClaims().get(authJwtProperty.email(), String.class);
        }
    }

    @Override
    public UUID getUserId(String accessToken) {
        try {
            String uuid = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(accessToken).getBody()
                    .get(authJwtProperty.id(), String.class);
            return UUID.fromString(uuid);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            String uuid = e.getClaims().get(authJwtProperty.id(), String.class);
            return UUID.fromString(uuid);
        }
    }

    @Override
    public long getValidTime(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(token).getBody();
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            claims = e.getClaims();
        }
        Date expire = claims.getExpiration();
        Date issued = claims.getIssuedAt();
        return expire.getTime() - issued.getTime();
    }

    @Override
    public List<String> getRoles(String accessToken) {
        try {
            return (List<String>) Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(accessToken)
                    .getBody().get(ROLE_NAME, Object.class);
        } catch(ExpiredJwtException e) {
            // 만료된 토큰이지만 Claims를 추출
            return (List<String>) e.getClaims().get(ROLE_NAME, Object.class);
        }
    }

    /**
     * 토큰 유효성 검사
     */
    @Override
    public void checkValidToken(String jwtToken, boolean checkExpire) {
        try {
            Jwts.parser().setSigningKey(authJwtProperty.secretKey()).parseClaimsJws(jwtToken);
        } catch(ExpiredJwtException e) {
            if(checkExpire) throw new TokenExpirationException();
        } catch(Exception e) {
            throw new JwtInvalidException();
        }
    }

    @Override
    public boolean isExpiredToken(String jwtToken) {
        try {
            checkValidToken(jwtToken, true);
            return false;
        } catch(TokenExpirationException e) {
            return true;
        }
    }
}
