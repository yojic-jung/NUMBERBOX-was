package com.numberbox.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberbox.security.dto.JwtAuthenticationToken;
import com.numberbox.security.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.util.*;

public class JwtRequestAuthProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtRequestAuthProvider(JwtUtil jwtUtil, ObjectMapper mapper) {
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String accessToken = (String) authentication.getPrincipal();
        String refreshToken = (String) authentication.getDetails();

        // check1. accessToken 존재하지만 refreshToken 미존재시 재로그인 필요
        if (accessToken != null && refreshToken == null) {
            throw new RefreshTokenNullException();
        }

        // accessToken(만료 여부 체크 안함, 리프레시 토큰이 재발급함으로) 유효성 검증
        boolean isAccessTokenValid = jwtUtil.validateTokenExceptExpiration(accessToken);
        // refreshToken 유효성 검증
        boolean isRefreshTokenValid = jwtUtil.validateToken(refreshToken);


        // check2. 하나라도 유효하지 않으면 토큰 사용불가
        if (!isAccessTokenValid || !isRefreshTokenValid) {
            // 클라이언트 환경에서 accessToken으로 로그인 관리하므로 액세스토큰 유무 확인
            // (로그인 되어있는 상태에서 토큰 만료시에만 로그인 재요청)
            if (jwtUtil.isValidButExpired(refreshToken)) {
                throw new RefreshTokenExpirationException(refreshToken);
            }
            throw new JwtInvalidException();
        }

        // payload에 저장된 사용자 인증 정보(userId, email, role)
        Map<String, Object> payloadMap = takePayloadMap(accessToken);

        // check3. accessToken과 refreshToken 발급 대상이 다른 경우
        UUID userUniqId = UUID.fromString(payloadMap.get("userUniqId").toString());
        boolean isTokenMatched = jwtUtil.checkTokenUserId(refreshToken, userUniqId);
        if (!isTokenMatched) {
            throw new RefreshTokenNotMachingException(refreshToken);
        }

        // accessToken 만료시 토큰 재생성 하여 Authentication 생성
        if (jwtUtil.isValidButExpired(accessToken)) {
            String email = payloadMap.get("email").toString();
            List<String> roleList = (List<String>) payloadMap.get("role");

            String newAccessToken = jwtUtil.createAccessToken(email, userUniqId, roleList);
            return jwtUtil.createAuthenticationByToken(newAccessToken);
        } else { // accessToken 만료 아닌 경우 기존 토큰 기반으로 Authentication 생성
            return jwtUtil.createAuthenticationByToken(accessToken);
        }
    }

    private Map<String, Object> takePayloadMap(String accessToken) {
        String[] check = accessToken.split("\\.");
        Base64.Decoder decoder = Base64.getDecoder();
        String payload = new String(decoder.decode(check[1]));
        try {
            return mapper.readValue(payload, HashMap.class);
        } catch (Exception e) {
            throw new AuthInternalException();
        }
    }

    /**
     * 전달 받은 authentication객체가 JwtAuthenticationToken 타입인 경우 provider가 활성화됨
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
