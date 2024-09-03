package com.numberbox.modules.auth.engine.util;

import com.numberbox.modules.auth.control.config.AuthConstantConfig;
import com.numberbox.modules.auth.engine.exception.JwtInvalidException;
import com.numberbox.modules.auth.engine.exception.TokenExpirationException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AuthJwtUtil implements AuthTokenUtil {
    private String secretKey;

    @Value("${numberbox.jwtSecretKey}")
    public void setSecretKey(String secretKey){
        this.secretKey = secretKey;
    }

    private static String EMAIL_KEY = "email";
    private static String USER_UNIQ_ID_KEY = "userUniqId";
    private static String ROLE_KEY = "roles";
    private static String DOMAIN = "nsoohak.com";
    private static String ISSUER = "nsoohak";
    private static String ACCESS_TOKEN_SUBJECT = "nsoohakAccessToken";
    private static String REFRESH_TOKEN_SUBJECT = "nsoohakRefreshToken";
    private static String AUDIENCE = "user";

    @Override
    public String createAccessToken(String email, UUID userUniqId, List<String> roleList) {
        Claims claims = Jwts.claims();
        claims.put(EMAIL_KEY, email);
        claims.put(USER_UNIQ_ID_KEY, userUniqId);
        claims.put(ROLE_KEY, roleList);
        claims.put(DOMAIN, true);

        // todo aop로 빼기
//        try {
//            String clientIp = request.getRemoteAddr();
//            if (clientIp == null)
//                logger.warn("예외 발생 : 접속 로그  clientIp null");
//
//            String osInfo = request.getHeader("sec-ch-ua-platform");
//            if (osInfo != null)
//                osInfo = osInfo.toLowerCase().replaceAll("\"", "");
//            else
//                logger.warn("예외 발생 : 접속 로그  osInfo null");
//
//            String browserInfo = request.getHeader("user-agent");
//            if (browserInfo != null)
//                browserInfo = browserInfo.toLowerCase().replaceAll("\"", "");
//            else
//                logger.warn("예외 발생 : 접속 로그  browserInfo null");
//
//            AccessLogInfoDto logInfoDto = this.covertIpOsBrowserInfo(clientIp, osInfo, browserInfo);
//            if (logInfoDto != null) {
//                logInfoDto.setUserUniqId(userUniqId);
//                AccessLogInfo logInfo = logInfoDto.toEntity();
//                accessLogInfoRepository.save(logInfo);
//            } else {
//                logger.warn("예외 발생 : 접속 로그 정보 null");
//            }
//        } catch (Exception e) {
//            logger.warn("예외 발생 : 접속 로그 에러" + e);
//        }

        Date now = new Date();
        Date expiration = new Date(now.getTime() + AuthConstantConfig.ACCESS_TOKEN_VALID_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setSubject(ACCESS_TOKEN_SUBJECT)
                .setAudience(AUDIENCE)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
//    public String createAccessTokenRoleStr(String email, UUID userUniqId,
//                                           List<String> role) {
//        Claims claims = Jwts.claims();
//        claims.put("userUniqId", userUniqId);
//        claims.put("role", role);
//        claims.put("email", email);
//        claims.put("nsoohak.com", true);
        // todo aop로 빼기
//        try {
//            String clientIp = request.getRemoteAddr();
//            String osInfo = request.getHeader("sec-ch-ua-platform").toLowerCase().replaceAll("\"", "");
//            String browserInfo = request.getHeader("user-agent").toLowerCase();
//
//            AccessLogInfoDto logInfoDto = this.covertIpOsBrowserInfo(clientIp, osInfo, browserInfo);
//            if (logInfoDto != null) {
//                logInfoDto.setUserUniqId(userUniqId);
//                AccessLogInfo logInfo = logInfoDto.toEntity();
//                accessLogInfoRepository.save(logInfo);
//            } else {
//                logger.warn("예외 발생 : 접속 로그 정보 null");
//            }
//        } catch (Exception e) {
//            logger.warn("예외 발생 : 접속 로그 에러");
//        }

        // 액세스 토큰 재발급시 사용자 마지막 로그인 날짜 초기화(자동 로그인으로 접속하는 경우, 액세스 토큰 유효기간 1시간)
//        membersRepository.initFailCntZeroAndLastLoginDate(userUniqId, LocalDateTime.now());
//        Date now = new Date();
//        return Jwts.builder().setClaims(claims).setIssuer("nsoohak").setSubject("nsoohakAccessToken")
//                .setAudience("user").setIssuedAt(now).setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
//                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
//    }

    @Override
    public String createRefreshToken() {
        Claims claims = Jwts.claims();
        claims.put(DOMAIN, true);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + AuthConstantConfig.REFRESH_TOKEN_VALID_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(ISSUER)
                .setSubject(REFRESH_TOKEN_SUBJECT)
                .setAudience(AUDIENCE)
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Override
    public String getEmail(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(EMAIL_KEY, String.class);
    }

    @Override
    public UUID getUserUniqId(String token) {
        String uuid = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get(USER_UNIQ_ID_KEY, String.class);
        return UUID.fromString(uuid);
    }

//    public List<String> getRole(String token) {
//        return (List<String>) Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody()
//                .get("role", Object.class);
//    }

    /**
     * 토큰 페이로드 추출
     */
//    @Override
//    public  Map<String, Object> takePayloadMap(String token) {
//        String[] check = token.split("\\.");
//        Base64.Decoder decoder = Base64.getDecoder();
//        String payload = new String(decoder.decode(check[1]));
//        try {
//            return mapper.readValue(payload, HashMap.class);
//        } catch (Exception e) {
//            throw new AuthInternalException();
//        }
//    }

//    @Transactional
//    public static void delRefreshToken(HttpServletRequest request, HttpServletResponse response) {
//        String jwtToken = "";
//        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
//        if (cookie != null) {
//            jwtToken = cookie.getValue();
//            cookie.setMaxAge(0);
//            cookie.setHttpOnly(true);
//            cookie.setPath("/");
//            cookie.setSecure(true);
//            cookie.setValue("");
//            response.addCookie(cookie);
//        }
//        refreshTokenService.deleteByToken(jwtToken);
//    }

    /**
     * 토큰 유효성 검사
     */
    @Override
    public void throwExceptionIfInvalidToken(String jwtToken) {
        throwExceptionIfInvalidToken(jwtToken, true);
    }

    /**
     * 토큰 유효성 검사(만료 여부 검사 지정 가능)
     */
    @Override
    public void throwExceptionIfInvalidToken(String jwtToken, boolean exceptExpiration) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e) {
            if(!exceptExpiration) throw new TokenExpirationException();
        }catch (Exception e) {
            throw new JwtInvalidException();
        }
    }

// todo
//    public AccessLogInfoDto covertIpOsBrowserInfo(String clientIp, String osInfo, String browserInfo) {
//        boolean isNullHeadInfo = false;
//        if (clientIp == null) {
//            logger.warn("client header informations are null");
//            isNullHeadInfo = true;
//        }
//        if (browserInfo == null) {
//            logger.warn("client header informations are null");
//            isNullHeadInfo = true;
//        }
//        if (osInfo == null) {
//            logger.warn("client header informations are null");
//            isNullHeadInfo = true;
//        }
//
//        if (!isNullHeadInfo) {
//            browserInfo = browserInfo.toLowerCase();
//            osInfo = osInfo.toLowerCase().replaceAll("\"", "");
//            if (osInfo.equals("windows")) {
//                if (browserInfo.contains("opr")) {
//                    browserInfo = "opr";
//                } else if (browserInfo.contains("edg")) {
//                    browserInfo = "edg";
//                } else if (browserInfo.contains("whale")) {
//                    browserInfo = "whale";
//                } else if (browserInfo.contains("firefox")) {
//                    browserInfo = "firefox";
//                } else if (browserInfo.contains("chrome")) {
//                    browserInfo = "chrome";
//                } else {
//                    browserInfo = "etc";
//                }
//            } else if (osInfo.equals("mac")) {
//                if (browserInfo.contains("opr")) {
//                    browserInfo = "opr";
//                } else if (browserInfo.contains("edg")) {
//                    browserInfo = "edg";
//                } else if (browserInfo.contains("whale")) {
//                    browserInfo = "whale";
//                } else if (browserInfo.contains("firefox")) {
//                    browserInfo = "firefox";
//                } else if (!(browserInfo.contains("chrome")) && browserInfo.contains("safari")) {
//                    browserInfo = "safari";
//                } else if (browserInfo.contains("chrome") && browserInfo.contains("safari")) {
//                    browserInfo = "chrome";
//                } else {
//                    browserInfo = "etc";
//                }
//            } else {
//                osInfo = "etc";
//                browserInfo = "etc";
//            }
//            AccessLogInfoDto logInfoDto = new AccessLogInfoDto();
//            logInfoDto.setClientIp(clientIp);
//            logInfoDto.setBrowserInfo(browserInfo);
//            logInfoDto.setOsInfo(osInfo);
//            return logInfoDto;
//        } else {
//            return null;
//        }
//    }
}
