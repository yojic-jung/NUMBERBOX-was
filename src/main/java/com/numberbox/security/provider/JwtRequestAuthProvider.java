package com.numberbox.security.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.numberbox.security.dto.JwtAuthenticationToken;
import com.numberbox.security.exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

import static com.numberbox.security.provider.JwtUtil.throwExceptionIfInvalidToken;

public class JwtRequestAuthProvider implements AuthenticationProvider {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final ObjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtRequestAuthProvider(UserDetailsService userDetailsService, JwtUtil jwtUtil, ObjectMapper mapper) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.mapper = mapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        // 클라이언트 요청에 포함된 토큰 추출
        String accessToken = (String) authentication.getPrincipal();
        String refreshToken = (String) authentication.getDetails();

        // check1. accessToken 또는 refreshToken 존재 여부 파악
        if (accessToken == null || refreshToken == null) throw new TokenNullException();

        // check2. 토큰 유효성 검사
        checkValidToken(accessToken, refreshToken);

        // accessToken에 저장된 사용자 인증 정보(userId, email, role) 추출
        Map<String, Object> userInfo = takePayloadMap(accessToken);

        // check3. refreshToken 소유자 체크(액세스 토큰 소유자와 같음)
        checkTokenOwner(userInfo.get("userUniqId").toString(), refreshToken);

        // Authentication 객체 반환
        return makeAuthentication(accessToken, userInfo);
    }

    /**
     * 토큰 유효성 검사
     */
    private void checkValidToken(String accessToken, String refreshToken){
        // accessToken 만료 여부 제외하고 유효성 검사
        boolean exceptExpire = true;
        throwExceptionIfInvalidToken(accessToken, exceptExpire);
        // refreshToken 유효성 검사
        try {
            throwExceptionIfInvalidToken(refreshToken);
        } catch (ExpiredJwtException ex) {
            // todo IP 체크하여 접속한 이력 있다면 리프레시 토큰도 재발급
        }
    }

    /**
     * 토큰 소유자 검사
     */
    private void checkTokenOwner(String userId, String refreshToken){
        UUID userUniqId = UUID.fromString(userId);
        boolean isTokenMatched = jwtUtil.checkTokenUserId(refreshToken, userUniqId);
        if (!isTokenMatched) {
            throw new TokenOwnerNotMatchingException(refreshToken);
        }
    }

    /**
     * Authentication(인증 정보) 반환
     */
    private Authentication makeAuthentication(String accessToken, Map<String, Object> userInfo){
        if (jwtUtil.isExpiredToken(accessToken)) {
            // accessToken 만료시 재생성하여 반환
            String email = userInfo.get("email").toString();
            UUID userUniqId = UUID.fromString(userInfo.get("userUniqId").toString());
            List<String> roleList = (List<String>) userInfo.get("role");

            String newAccessToken = jwtUtil.createAccessToken(email, userUniqId, roleList);
            return jwtUtil.createAuthenticationByToken(newAccessToken);
        } else {
            // accessToken 만료 아닌 경우 기존 토큰 기반으로 반환
            return jwtUtil.createAuthenticationByToken(accessToken);
        }
    }


    /**
     * 토큰 페이로드 추출
     */
    private Map<String, Object> takePayloadMap(String token) {
        String[] check = token.split("\\.");
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
