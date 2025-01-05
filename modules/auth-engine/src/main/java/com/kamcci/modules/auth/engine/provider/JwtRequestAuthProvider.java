package com.kamcci.modules.auth.engine.provider;

import com.kamcci.modules.auth.control.service.JwtRequestUserDetailService;
import com.kamcci.modules.auth.engine.dto.AuthUserDetail;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.RefreshTokenNullException;
import com.kamcci.modules.auth.engine.exception.TokenOwnerNotMatchingException;
import com.kamcci.modules.auth.engine.util.AuthTokenUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        String refreshToken = (String) authentication.getDetails();

        // check1. refreshToken 존재 여부 파악(accessToken은 존재함, 필터가 액세스 토큰 있는 경우에만 실행)
        if(refreshToken == null) throw new RefreshTokenNullException();

        // check2. 토큰 유효성 검사
        authTokenUtil.checkValidToken(accessToken, false);
        authTokenUtil.checkValidToken(refreshToken, true);

        // 서버에서 사용자 정보 조회
        final String email = authTokenUtil.getEmail(accessToken);
        final AuthUserDetail user = (AuthUserDetail) userDetailsService.loadUserByUsername(email);

        // check3. refreshToken 소유자 체크(액세스 토큰 소유자와 같아야함)
        final String accessTokenOwner = authTokenUtil.getUserUniqId(accessToken).toString();
        checkTokenOwner(accessTokenOwner, refreshToken);

        // check4. enabled 체크해야함
        if(!user.isEnabled()) throw new DisabledException("비활성 계정입니다.");

        // Authentication 객체 반환
        return makeAuthentication(user);
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
    private Authentication makeAuthentication(AuthUserDetail user) {
        final UUID userUniqId = user.getUserId();
        final List<GrantedAuthority> authorities = new ArrayList<>(user.getAuthorities());

        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getUsername(), "",
                authorities);
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
