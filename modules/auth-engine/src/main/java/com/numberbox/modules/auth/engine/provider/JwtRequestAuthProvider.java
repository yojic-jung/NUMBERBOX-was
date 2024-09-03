package com.numberbox.modules.auth.engine.provider;

import com.numberbox.modules.auth.engine.service.JwtRequestUserDetailServiceWrapper;
import com.numberbox.modules.auth.engine.util.AuthTokenUtil;
import com.numberbox.modules.auth.engine.dto.AuthUserDetail;
import com.numberbox.modules.auth.engine.dto.JwtAuthenticationToken;
import com.numberbox.modules.auth.engine.exception.RefreshTokenNullException;
import com.numberbox.modules.auth.engine.exception.TokenOwnerNotMatchingException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.*;
import java.util.stream.Collectors;

public class JwtRequestAuthProvider implements AuthenticationProvider {
    private final JwtRequestUserDetailServiceWrapper userTokenDetailService;
    private final AuthTokenUtil authTokenUtil;

    public JwtRequestAuthProvider(JwtRequestUserDetailServiceWrapper userTokenDetailService, AuthTokenUtil authTokenUtil) {
        this.userTokenDetailService = userTokenDetailService;
        this.authTokenUtil = authTokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        // 클라이언트 요청에 포함된 토큰 추출
        final String accessToken = (String) authentication.getPrincipal();
        final String refreshToken = (String) authentication.getDetails();

        // check1. refreshToken 존재 여부 파악(accessToken은 존재함, 필터가 액세스 토큰 있는 경우에만 실행)
        if (refreshToken == null) throw new RefreshTokenNullException();

        // check2. 토큰 유효성 검사
        checkValidToken(accessToken, refreshToken);

        // 서버에서 사용자 정보 조회
        final String email = authTokenUtil.getEmail(accessToken);
        final AuthUserDetail user =
                (AuthUserDetail) userTokenDetailService.loadUserByUsername(email);

        // check3. refreshToken 소유자 체크(액세스 토큰 소유자와 같아야함)
        final String accessTokenOwner = authTokenUtil.getUserUniqId(accessToken).toString();
        checkTokenOwner(accessTokenOwner, refreshToken);

        // check4. enabled 체크해야함
        if (!user.isEnabled()) throw new DisabledException("비활성 계정입니다.");

        // Authentication 객체 반환
        return makeAuthentication(user);
    }

    /**
     * 토큰 유효성 검사
     */
    private void checkValidToken(String accessToken, String refreshToken) {
        // accessToken 만료 여부 제외하고 유효성 검사
        boolean exceptExpire = true;
        authTokenUtil.throwExceptionIfInvalidToken(accessToken, exceptExpire);
        // refreshToken 유효성 검사
        try {
            authTokenUtil.throwExceptionIfInvalidToken(refreshToken);
        } catch (ExpiredJwtException ex) {
            // todo IP 체크하여 접속한 이력 있다면 리프레시 토큰도 재발급
        }
    }

    /**
     * 토큰 소유자 검사
     */
    private void checkTokenOwner(String userId, String refreshToken) {
        final UUID clientUserId = UUID.fromString(userId);
        final UUID serverUserId = userTokenDetailService.loadUserIdByRefreshToken(refreshToken);

        if (!clientUserId.equals(serverUserId)) throw new TokenOwnerNotMatchingException();
    }

    /**
     * Authentication(인증 정보) 반환
     */
    private Authentication makeAuthentication(AuthUserDetail user) {
        final UUID userUniqId = user.getUserId();
        final List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        UsernamePasswordAuthenticationToken auth
                = new UsernamePasswordAuthenticationToken(user.getUsername(), "", authorities);
        auth.setDetails(userUniqId);
        return auth;
    }

    /**
     * 전달 받은 authentication객체가 JwtAuthenticationToken 타입인 경우 provider가 활성화됨
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
