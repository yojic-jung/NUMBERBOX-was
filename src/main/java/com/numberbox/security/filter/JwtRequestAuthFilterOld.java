//package com.numberbox.security.filter;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.numberbox.security.provider.JwtUtil;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.List;
//import java.util.UUID;
//
//public class JwtRequestAuthFilterOld extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    public JwtRequestAuthFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    // accessToken 존재시 accessToken 정보로 인증 객체 설정
//    // 스프링 시큐리티는 동일 쓰레드(사용자 요청이 오면 하나의 쓰레드 할당됨)에서 같은 인증정보로 접근 가능
//    // 사용자 요청에 대해 accessToken 존재 시 doFilterInternal에서 인증 정보 객체 생성하니 이후 서버단 로직에서 인증정보
//    // 객체 사용가능
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        String accessToken = jwtUtil.resolveAccessToken(request);
//        String refreshToken = jwtUtil.resolveRefreshToken(request);
//        // access(만료여부 상관없이) & refresh 토큰 유효성만 검증
//        boolean isAccessTokenValid = accessToken != null && jwtUtil.throwExceptionIfInvalidToken(accessToken);
//        boolean isRefreshTokenValid = refreshToken != null && jwtUtil.throwExceptionIfInvalidToken(refreshToken);
//        // 하나라도 유효하지 않으면 토큰 사용불가
//        if (!isAccessTokenValid || !isRefreshTokenValid) {
//            if (accessToken != null) { // 클라이언트 환경에서 accessToken으로 로그인 관리하므로 액세스토큰 유무 확인(로그인 되어있는 상태에서 토큰 만료시에만 로그인
//                // 재요청)
//                jwtUtil.delRefreshToken(request, response);
//                response.setHeader("tokenExpired", "expire");
//            }
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 만료여부까지 파악
//        isAccessTokenValid = jwtUtil.throwExceptionIfInvalidToken(accessToken);
//
//        try {
//            String[] check = accessToken.split("\\.");
//            Base64.Decoder decoder = Base64.getDecoder();
//            String payload = new String(decoder.decode(check[1]));
//            ObjectMapper mapper = new ObjectMapper();
//            HashMap<String, Object> returnMap = mapper.readValue(payload, HashMap.class);
//            UUID userUniqId = UUID.fromString(returnMap.get("userUniqId").toString());
//
//            // refresh토큰과 access토큰 발급자가 같은지 검증
//            boolean isTokenMatched = jwtUtil.checkTokenUserId(refreshToken, userUniqId);
//            if (!isTokenMatched) {
//                // 발급자 다르다면 리프레시 토큰 삭제
//                jwtUtil.delRefreshToken(request, response);
//                response.setHeader("tokenExpired", "expire");
//                filterChain.doFilter(request, response);
//                return;
//            }
//
//            if (isAccessTokenValid) { // accessToken 유효시
//                Authentication authentication = jwtUtil.getAuthentication(accessToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else { // accessToken 만료시 토큰 재생성
//                String email = returnMap.get("email").toString();
//                List<String> roleList = (List<String>) returnMap.get("role");
//
//                // todo 롤 바꿔야함
//                // 매니저 권한 임시 구현
//                boolean isManager = false;
//                boolean isTopTester = false;
//                boolean isAdmin = false;
//                for (String role : roleList) {
//                    if (role.contains("ADMIN")) {
//                        isAdmin = true;
//                    } else if (role.contains("TOP_TESTER")) {
//                        isTopTester = true;
//                    } else if (role.contains("MANAGER")) {
//                        isManager = true;
//                    }
//                }
//
//                if (isAdmin) {
//                    response.setHeader("role", "ADMIN");
//                } else if (!isAdmin && isTopTester) {
//                    response.setHeader("role", "TOP_TESTER");
//                } else if (!isAdmin && isManager) {
//                    response.setHeader("role", "MANAGER");
//                } else {
//                    response.setHeader("role", "USER");
//                }
//
//                String newAccessToken = jwtUtil.createAccessTokenRoleStr(request, email, userUniqId, roleList);
//                response.setHeader("access-token", newAccessToken);
//                Authentication authentication = jwtUtil.getAuthentication(newAccessToken);
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            }
//
//        } catch (Exception e) {
//            logger.warn("jwt토큰 검증 에러 : " + e);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    private void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
//                                          Authentication authResult) {
//        System.out.println("222");
//
//        // todo 성공하면 사용자 로그 찍어야함(IP)
//    }
//
//    private void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
//                                            AuthenticationException failed) {
//    }
//
//}
