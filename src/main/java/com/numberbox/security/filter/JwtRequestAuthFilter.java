package com.numberbox.security.filter;

import com.numberbox.security.dto.JwtAuthenticationToken;
import com.numberbox.security.exception.*;
import com.numberbox.security.provider.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.numberbox.security.util.SecurityUtil.responseErrMsg;

public class JwtRequestAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtRequestAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try{
            // 클라이언트 토큰 추출
            String accessToken = JwtUtil.resolveAccessToken(request);
            String refreshToken = JwtUtil.resolveRefreshToken(request);

            // 클라이언트 인증 객체 생성
            JwtAuthenticationToken authRequest
                    = new JwtAuthenticationToken(accessToken, null, null);
            authRequest.setDetails(refreshToken);
            
            // 인증 요청
            Authentication authentication = authenticationManager.authenticate(authRequest);

            // SecurityContextHolder에 인증정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        }catch (Exception exception){
            // 인증 실패 핸들러 호출
            unsuccessfulAuthentication(response, exception);
        }
    }

    private void unsuccessfulAuthentication(HttpServletResponse response, Exception exception) {
        // 인증 실패 응답 메시지 전송
        if(exception instanceof TokenException) {
            responseErrMsg(response, HttpStatus.FORBIDDEN, exception.getMessage());
        } else if(exception instanceof DisabledException) {
            responseErrMsg(response, HttpStatus.FORBIDDEN, exception.getMessage());
        } else if(exception instanceof Exception) {
            logger.warn("jwt 인증 과정 중 예외 발생 : "+exception);
            responseErrMsg(response, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String accessToken = JwtUtil.resolveAccessToken(request);
        return accessToken == null;
    }
}
