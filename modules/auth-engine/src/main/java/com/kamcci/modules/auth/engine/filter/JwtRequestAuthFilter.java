package com.kamcci.modules.auth.engine.filter;

import com.kamcci.modules.auth.control.config.AuthConstantConfig;
import com.kamcci.modules.auth.engine.dto.JwtAuthenticationToken;
import com.kamcci.modules.auth.engine.exception.TokenException;
import com.kamcci.modules.auth.engine.util.AuthWebUtil;
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

public class JwtRequestAuthFilter extends OncePerRequestFilter {
    private final AuthenticationManager authenticationManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public JwtRequestAuthFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            // 클라이언트 토큰 추출
            String accessToken = request.getHeader(AuthConstantConfig.ACCESS_TOKEN_NAME).split(" ")[1];
            String refreshToken = AuthWebUtil.getCookieValue(request, AuthConstantConfig.REFRESH_TOKEN_NAME);

            // 클라이언트 인증 객체 생성
            JwtAuthenticationToken authRequest = new JwtAuthenticationToken(accessToken, null, null);
            authRequest.setDetails(refreshToken);

            // 인증 요청
            Authentication authentication = authenticationManager.authenticate(authRequest);

            // SecurityContextHolder에 인증정보 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch(Exception exception) {
            // 인증 실패 핸들러 호출
            unsuccessfulAuthentication(response, exception);
        }
    }

    private void unsuccessfulAuthentication(HttpServletResponse response, Exception exception) {
        // 인증 실패 응답 메시지 전송
        if(exception instanceof TokenException) {
            // todo 리프레시 토큰 관련 에러 이벤트 발행??? 리프레시 토큰 DB에서 삭제 하도록
            AuthWebUtil.responseErrMsg(response, HttpStatus.FORBIDDEN, exception.getMessage());
        } else if(exception instanceof DisabledException) {
            AuthWebUtil.responseErrMsg(response, HttpStatus.FORBIDDEN, exception.getMessage());
        } else if(exception instanceof Exception) {
            logger.warn("jwt 인증 과정 중 예외 발생 : " + exception);
            // todo 서버에러는 프로젝트로 보내줘서 에러 로깅 해야함
            // 현재 서버에서 예외 캐치하는 로직이 없음
            AuthWebUtil.responseErrMsg(response, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        final String accessToken = request.getHeader(AuthConstantConfig.ACCESS_TOKEN_NAME);
        return accessToken == null;
    }
}
