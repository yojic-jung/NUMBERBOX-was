package com.numberbox.jwt.util;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JwtAuthenticationFilter  extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil  jwtUtil) {
    	this.jwtUtil = jwtUtil;
    }
    
    //accessToken 존재시 accessToken 정보로 인증 객체 설정
    //스프링 시큐리티는 동일 쓰레드(사용자 요청이 오면 하나의 쓰레드 할당됨)에서 같은 인증정보로 접근 가능
    //사용자 요청에 대해 accessToken 존재 시 doFilterInternal에서 인증 정보 객체 생성하니 이후 서버단 로직에서 인증정보 객체 사용가능
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtUtil.resolveAccessToken(request);
        String refreshToken = jwtUtil.resolveRefreshToken(request);
        boolean isAccessTokenValid = accessToken != null && jwtUtil.validateToken(accessToken);

        try {
        	//accessToken 유효시 기존 토큰 정보로 인증객체 생성
            if (isAccessTokenValid) {
            	if(refreshToken != null) {
            		Authentication authentication = jwtUtil.getAuthentication(accessToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                //access토큰 유효한 상태에서 리프레시 토큰 없는 경우
            	}else {
            		response.setHeader("tokenExpired", "expire");
            	}
                
            }
            //accessToken 유효하지 않은 경우
            else {
                boolean isExpired = jwtUtil.validateTokenExceptExpiration(refreshToken) || jwtUtil.validateRefreshToken((refreshToken));
                if (refreshToken != null ) {
                	//refreshToken 유효시 accessToken 새로 생성, refreshToken은 새로 생성하지 않음
                	if(!isExpired) {
                		String[] check = accessToken.split("\\.");
                        Base64.Decoder decoder = Base64.getDecoder();
                        String payload = new String(decoder.decode(check[1]));
                        ObjectMapper mapper = new ObjectMapper();
                        HashMap<String, Object> returnMap = mapper.readValue(payload, HashMap.class);
                        String email = returnMap.get("sub").toString();
                        long userNo = Long.parseLong(returnMap.get("userNo").toString());
                        List<String> roleList = (List<String>)returnMap.get("role");
                        String newAccessToken = jwtUtil.createAccessTokenRoleStr(email, userNo, roleList);
                        response.setHeader("access-token", newAccessToken);
                        Authentication authentication = jwtUtil.getAuthentication(newAccessToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    //access토큰 만료, refresh토큰 만료
                	}else {
                		// 오류를 반환해, 사용자에게 로그인을 요구한다. permitAll인 경우 상관 없으므로 권한체크
                		response.setHeader("tokenExpired", "expire");
                	}
                }
               
            }
        } catch (Exception e) {

        }
        filterChain.doFilter(request, response);
    }
}
