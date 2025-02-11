package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.annotation.UserId;
import com.kamcci.modules.auth.control.exception.RefreshTokenNullException;
import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.TokenExpirationException;
import com.kamcci.modules.auth.engine.exception.TokenOwnerNotMatchingException;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.*;

public class JwtRequestAuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final JwtRequestUserDetailService jwtRequestUserDetailService;
    private final AuthTokenUtil authTokenUtil;

    public JwtRequestAuthProvider(UserDetailsService userDetailsService,
                                  JwtRequestUserDetailService jwtRequestUserDetailService,
                                  AuthTokenUtil authTokenUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtRequestUserDetailService = jwtRequestUserDetailService;
        this.authTokenUtil = authTokenUtil;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        // 클라이언트 요청에 포함된 토큰 추출
        String accessToken = (String) authentication.getPrincipal();

        // check1. refreshToken 존재 여부 파악(accessToken은 존재함, 필터가 액세스 토큰 있는 경우에만 실행)
        String refreshToken = null;
        Map<String, Object> details = (Map<String, Object>) authentication.getDetails();
        if(details != null) refreshToken = (String) details.get("refreshToken");
        if(refreshToken == null) throw new RefreshTokenNullException();

        // check2. 토큰 유효성 검사
        authTokenUtil.checkValidToken(accessToken, false);
        boolean reCreateRefreshToken = false;
        boolean isExpire = authTokenUtil.isExpiredToken(refreshToken);
        if(isExpire) {
            // 리프로시 토큰 만료된 경우 ip 체크하여 재발급
            reCreateRefreshToken = canReCreateRefreshToken(accessToken);
            if(!reCreateRefreshToken) throw new TokenExpirationException();
        }

        // 서버에서 사용자 정보 조회
        final String email = authTokenUtil.getEmail(accessToken);
        final AuthUserDetail user = (AuthUserDetail) userDetailsService.loadUserByUsername(email);

        // check3. enabled 체크해야함
        if(!user.isEnabled()) throw new DisabledException("비활성 계정입니다.");

        // check4. refreshToken 소유자 체크(액세스 토큰 소유자와 같아야함)
        final String accessTokenOwner = authTokenUtil.getUserId(accessToken).toString();
        checkTokenOwner(accessTokenOwner, refreshToken);

        // Authentication 객체 반환
        return makeAuthentication(user, reCreateRefreshToken ? refreshToken : null);
    }

    /**
     * accessToken 정보로 부터 리프레시 토큰 재발급 가능 여부 판별
     */
    private boolean canReCreateRefreshToken(String accessToken) {
        final UUID userId = authTokenUtil.getUserId(accessToken);
        return jwtRequestUserDetailService.canReCreateRefreshToken(userId);
    }

    /**
     * 토큰 소유자 검사
     */
    private void checkTokenOwner(String userId, String refreshToken) {
        final UUID clientUserId = UUID.fromString(userId);
        final UUID serverUserId = jwtRequestUserDetailService.loadUserIdByRefreshToken(refreshToken);

        if(!clientUserId.equals(serverUserId)) throw new TokenOwnerNotMatchingException();
    }

    /**
     * Authentication(인증 정보) 반환
     */
    private Authentication makeAuthentication(AuthUserDetail user, String oldRefreshToken) {
        final UUID userId = user.getUserId();
        final List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), "",
                authorities);
        Map<String, Object> details = new HashMap<>();
        details.put(UserId.ATTR_NAME, userId);
        if(oldRefreshToken != null) details.put("oldRefreshToken", oldRefreshToken);
        auth.setDetails(details);
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
