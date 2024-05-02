package com.numberbox.security.filter;

import com.numberbox.security.dto.JwtAuthenticationToken;
import com.numberbox.security.handler.JwtAuthFailureHandler;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtRequestAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final JwtAuthFailureHandler jwtAuthFailureHandler;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtRequestAuthFilter(AuthenticationManager authenticationManager, JwtUtil jwtUtil,
                                JwtAuthFailureHandler jwtAuthFailureHandler) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.jwtAuthFailureHandler = jwtAuthFailureHandler;
    }

    // accessToken 존재시 accessToken 정보로 인증 객체 설정
    // 스프링 시큐리티는 동일 쓰레드(사용자 요청이 오면 하나의 쓰레드 할당됨)에서 같은 인증정보로 접근 가능
    // 사용자 요청에 대해 accessToken 존재 시 doFilterInternal에서 인증 정보 객체 생성하니 이후 서버단 로직에서 인증정보
    // 객체 사용가능
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try{
            String accessToken = jwtUtil.resolveAccessToken(request);
            String refreshToken = jwtUtil.resolveRefreshToken(request);

            JwtAuthenticationToken authRequest
                    = new JwtAuthenticationToken(accessToken, null, null);
            authRequest.setDetails(refreshToken);
            // 인증 요청
            Authentication authentication = authenticationManager.authenticate(authRequest);

            // SecurityContextHolder에 인증정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 인증 성공 핸들러 호출
            successfulAuthentication(request, response, filterChain, authentication);
            filterChain.doFilter(request, response);
        }catch (Exception exception){
            // 인증 실패 핸들러 호출
            unsuccessfulAuthentication(response, exception);
        }
    }


    private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                          Authentication authResult) {
        // todo response.setHeader("role", 롤 정보), 롤정보 이메일, jwt 통일 필요, 프론트 테스트 필요
        // response 헤더에 전달되는 값과 jwt 출력값 비교
        // todo 성공하면 사용자 로그 찍어야함(IP)
    }

    private void unsuccessfulAuthentication(HttpServletResponse response, Exception exception) {
        jwtAuthFailureHandler.onAuthenticationFailure(response, exception);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String accessToken = jwtUtil.resolveAccessToken(request);
        return accessToken == null;
    }
}
