package com.numberbox.security.provider;

import com.numberbox.security.dto.AuthUserDetail;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;

/**
 * Def. 인증 처리
 * - 사용자 요청 인증 정보와 서버에 저장된 인증 정보를 비교하여 인증 처리를 진행함
 * - 인증된 정보 객체 반환
 */
public class LoginRequestAuthProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public LoginRequestAuthProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 서버(DB)에 저장된 사용자 인증 정보 추출
        AuthUserDetail serverUserInfo =
                (AuthUserDetail) userDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        // 유효성 체크와 함께 인증정보 추출
        return takeValidAuthentication(authentication, serverUserInfo);
    }

    /**
     * 사용자 요청 인증 정보와 서버에 저장된 사용자 인증 정보 비교하여 유효성 체크
     */
    private Authentication takeValidAuthentication(Authentication clientUserInfo, AuthUserDetail serverUserInfo) {
        // check1. 계정 존재 여부 체크
        if (serverUserInfo == null) throw new UsernameNotFoundException("해당 계정이 없습니다.");

        // check2. password 같은지 비교
        String password = serverUserInfo.getPassword();
        if (!passwordEncoder.matches((String) clientUserInfo.getCredentials(), password))
            throw new BadCredentialsException("비밀번호가 일치 하지 않습니다.");

        // check3. 활성 계정 체크
        if (!serverUserInfo.isEnabled()) throw new DisabledException("비활성 계정입니다.");

        // Authentication 반환 (authorities 주입하면 authenticated 속성 자동으로 true 설정됨)
        String username = serverUserInfo.getUsername();
        Collection<? extends GrantedAuthority> authorities = serverUserInfo.getAuthorities();
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password, authorities);
        token.setDetails(serverUserInfo.getUserId());
        return token;
    }

    /**
     * 전달 받은 authentication객체가 UsernamePasswordAuthenticationToken 타입인 경우 provider가 활성화됨
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
