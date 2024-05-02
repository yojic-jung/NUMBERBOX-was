package com.numberbox.security.provider;

import com.numberbox.jwt.service.RefreshTokenInfoService;
import com.numberbox.members.dto.AccessLogInfoDto;
import com.numberbox.security.dto.AuthUserInfo;
import com.numberbox.security.exception.JwtInvalidException;
import com.numberbox.security.exception.TokenExpirationException;
import com.numberbox.security.service.LoginRequestUserDetailService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    // todo 두군데서 사용하고 있음
    private static final String ROLE_PREFIX = "ROLE_";
    @Autowired
    private RefreshTokenInfoService refreshTokenService;
    @Autowired
    private LoginRequestUserDetailService loginRequestUserService;
//    @Autowired
//    private MembersRepository membersRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    private static String secretKey;
    // todo 사용자 설정 상수로 빼기
    private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간
    public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; // 1달 (로그인 유지 요청한 경우)
    public static final long REFRESH_TOKEN_VALID_TIME_DEFAULT = 1000L * 60 * 60 * 6; // 6시간

    @Value("${numberbox.jwtSecretKey}")
    public void setSecretKey(String secretKey){
        this.secretKey = secretKey;
    }

    public String resolveAccessToken(HttpServletRequest request) {
        String token = request.getHeader("access-token");
        if (token != null && token.equals("null"))
            token = null;
        return token;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String token = null;
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if (cookie != null)
            token = cookie.getValue();
        return token;
    }

    public String createAccessToken(String email, UUID userUniqId, List<String> roleList) {
        Claims claims = Jwts.claims();
        claims.put("email", email);
        claims.put("userUniqId", userUniqId);
        claims.put("role", roleList);
        claims.put("nsoohak.com", true);

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
        Date expiration = new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("nsoohak")
                .setSubject("nsoohakAccessToken")
                .setAudience("user")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public String createAccessTokenRoleStr(String email, UUID userUniqId,
                                           List<String> role) {
        Claims claims = Jwts.claims();
        claims.put("userUniqId", userUniqId);
        claims.put("role", role);
        claims.put("email", email);
        claims.put("nsoohak.com", true);
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
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuer("nsoohak").setSubject("nsoohakAccessToken")
                .setAudience("user").setIssuedAt(now).setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public String createRefreshToken() {
        Claims claims = Jwts.claims();
        claims.put("nsoohak.com", true);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME_DEFAULT);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("nsoohak")
                .setSubject("nsoohakRefreshToken")
                .setAudience("user")
                .setExpiration(expiration)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // todo userDetailService
    public Authentication createAuthenticationByToken(String token) {
        String email = getUserEmail(token);
        AuthUserInfo user = loginRequestUserService.loadUserByUsername(email);
        List<GrantedAuthority> list = new ArrayList<>();
        user.roles().forEach(userRole -> list.add(new SimpleGrantedAuthority(ROLE_PREFIX + userRole.roleName())));
        UsernamePasswordAuthenticationToken auth
                = new UsernamePasswordAuthenticationToken(user.username(), "", list);
        auth.setDetails(user.userId());
        return auth;
    }

    public String getUserEmail(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
    }

    public UUID getUserUniqId(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .get("userUniqId", UUID.class);
    }


    public boolean checkTokenUserId(String jwtToken, UUID userUniqId) {
        return refreshTokenService.isTokenMatched(jwtToken, userUniqId);
    }

    @Transactional
    public void delRefreshToken(HttpServletRequest request, HttpServletResponse response) {
        String jwtToken = "";
        Cookie cookie = WebUtils.getCookie(request, "refresh-token");
        if (cookie != null) {
            jwtToken = cookie.getValue();
            cookie.setMaxAge(0);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);
            cookie.setValue("");
            response.addCookie(cookie);
        }
        refreshTokenService.deleteByToken(jwtToken);
    }

    /**
     * 토큰 유효성 검사
     */
    public static void throwExceptionIfInvalidToken(String jwtToken) {
        throwExceptionIfInvalidToken(jwtToken, true);
    }

    /**
     * 토큰 유효성 검사(만료 여부 검사 지정 가능)
     */
    public static void throwExceptionIfInvalidToken(String jwtToken, boolean exceptExpiration) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e) {
            if(!exceptExpiration) throw new TokenExpirationException();
        }catch (Exception e) {
            throw new JwtInvalidException();
        }
    }

    /**
     * 토큰은 유효하나 만료된 경우에만 true 리턴
     */
    public boolean isExpiredToken(String jwtToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return false;
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public AccessLogInfoDto covertIpOsBrowserInfo(String clientIp, String osInfo, String browserInfo) {
        boolean isNullHeadInfo = false;
        if (clientIp == null) {
            logger.warn("client header informations are null");
            isNullHeadInfo = true;
        }
        if (browserInfo == null) {
            logger.warn("client header informations are null");
            isNullHeadInfo = true;
        }
        if (osInfo == null) {
            logger.warn("client header informations are null");
            isNullHeadInfo = true;
        }

        if (!isNullHeadInfo) {
            browserInfo = browserInfo.toLowerCase();
            osInfo = osInfo.toLowerCase().replaceAll("\"", "");
            if (osInfo.equals("windows")) {
                if (browserInfo.contains("opr")) {
                    browserInfo = "opr";
                } else if (browserInfo.contains("edg")) {
                    browserInfo = "edg";
                } else if (browserInfo.contains("whale")) {
                    browserInfo = "whale";
                } else if (browserInfo.contains("firefox")) {
                    browserInfo = "firefox";
                } else if (browserInfo.contains("chrome")) {
                    browserInfo = "chrome";
                } else {
                    browserInfo = "etc";
                }
            } else if (osInfo.equals("mac")) {
                if (browserInfo.contains("opr")) {
                    browserInfo = "opr";
                } else if (browserInfo.contains("edg")) {
                    browserInfo = "edg";
                } else if (browserInfo.contains("whale")) {
                    browserInfo = "whale";
                } else if (browserInfo.contains("firefox")) {
                    browserInfo = "firefox";
                } else if (!(browserInfo.contains("chrome")) && browserInfo.contains("safari")) {
                    browserInfo = "safari";
                } else if (browserInfo.contains("chrome") && browserInfo.contains("safari")) {
                    browserInfo = "chrome";
                } else {
                    browserInfo = "etc";
                }
            } else {
                osInfo = "etc";
                browserInfo = "etc";
            }
            AccessLogInfoDto logInfoDto = new AccessLogInfoDto();
            logInfoDto.setClientIp(clientIp);
            logInfoDto.setBrowserInfo(browserInfo);
            logInfoDto.setOsInfo(osInfo);
            return logInfoDto;
        } else {
            return null;
        }
    }
}
