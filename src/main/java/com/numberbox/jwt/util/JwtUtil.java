package com.numberbox.jwt.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.WebUtils;

import com.numberbox.jwt.service.RefreshTokenInfoService;
import com.numberbox.members.dto.AccessLogInfoDto;
import com.numberbox.members.entity.AccessLogInfo;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.AccessLogInfoRepository;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.security.service.CustomSecurityUsersService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	@Autowired
	private RefreshTokenInfoService refreshTokenService;
	@Autowired
	private CustomSecurityUsersService customUsersService;
	@Autowired
	private MembersRepository membersRepository;
	@Autowired
	private AccessLogInfoRepository accessLogInfoRepository;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Value("${numberbox.jwtSecretKey}")
	private String secretKey;

	private static final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; // 1시간
	public static final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; // 1달

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String createAccessToken(HttpServletRequest request, String email, UUID userUniqId,
			List<MembersRole> roleList) {
		List<String> strRoleList = new ArrayList<>();
		for (MembersRole role : roleList) {
			strRoleList.add(role.getRoleName());
		}

		Claims claims = Jwts.claims();
		claims.put("userUniqId", userUniqId);
		claims.put("role", strRoleList);
		claims.put("email", email);
		claims.put("nsoohak.com", true);

		try {
			String clientIp = request.getRemoteAddr();
			if (clientIp == null)
				logger.warn("예외 발생 : 접속 로그  clientIp null");

			String osInfo = request.getHeader("sec-ch-ua-platform");
			if (osInfo != null)
				osInfo = osInfo.toLowerCase().replaceAll("\"", "");
			else
				logger.warn("예외 발생 : 접속 로그  osInfo null");

			String browserInfo = request.getHeader("user-agent");
			if (browserInfo != null)
				browserInfo = browserInfo.toLowerCase().replaceAll("\"", "");
			else
				logger.warn("예외 발생 : 접속 로그  browserInfo null");

			AccessLogInfoDto logInfoDto = this.covertIpOsBrowserInfo(clientIp, osInfo, browserInfo);
			if (logInfoDto != null) {
				logInfoDto.setUserUniqId(userUniqId);
				AccessLogInfo logInfo = logInfoDto.toEntity();
				accessLogInfoRepository.save(logInfo);
			} else {
				logger.warn("예외 발생 : 접속 로그 정보 null");
			}
		} catch (Exception e) {
			logger.warn("예외 발생 : 접속 로그 에러" + e);
		}

		Date now = new Date();
		return Jwts.builder().setClaims(claims).setIssuer("nsoohak").setSubject("nsoohakAccessToken")
				.setAudience("user").setIssuedAt(now).setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public String createAccessTokenRoleStr(HttpServletRequest request, String email, UUID userUniqId,
			List<String> roleList) {
		Claims claims = Jwts.claims();
		claims.put("userUniqId", userUniqId);
		claims.put("role", roleList);
		claims.put("email", email);
		claims.put("nsoohak.com", true);

		try {
			String clientIp = request.getRemoteAddr();
			String osInfo = request.getHeader("sec-ch-ua-platform").toLowerCase().replaceAll("\"", "");
			String browserInfo = request.getHeader("user-agent").toLowerCase();

			AccessLogInfoDto logInfoDto = this.covertIpOsBrowserInfo(clientIp, osInfo, browserInfo);
			if (logInfoDto != null) {
				logInfoDto.setUserUniqId(userUniqId);
				AccessLogInfo logInfo = logInfoDto.toEntity();
				accessLogInfoRepository.save(logInfo);
			} else {
				logger.warn("예외 발생 : 접속 로그 정보 null");
			}
		} catch (Exception e) {
			logger.warn("예외 발생 : 접속 로그 에러");
		}

		// 액세스 토큰 재발급시 사용자 마지막 로그인 날짜 초기화(자동 로그인으로 접속하는 경우, 액세스 토큰 유효기간 1시간)
		membersRepository.initLastLoginDate(userUniqId, LocalDateTime.now());
		Date now = new Date();
		return Jwts.builder().setClaims(claims).setIssuer("nsoohak").setSubject("nsoohakAccessToken")
				.setAudience("user").setIssuedAt(now).setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public String createRefreshToken(String email, UUID userUniqId) {
		Claims claims = Jwts.claims();
		claims.put("nsoohak.com", true);
		Date now = new Date();
		Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);

		return Jwts.builder().setClaims(claims).setIssuer("nsoohak").setSubject("nsoohakRefreshToken")
				.setAudience("user").setExpiration(expiration).setIssuedAt(now)
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public Authentication getAuthentication(String token) {
		String email = getUserEmail(token);
		UserDetails user = customUsersService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	}

	public String getUserEmail(String token) {
		return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
	}

	public UUID getUserUniqId(String token) {
		return (UUID) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userUniqId",
				UUID.class);
	}

	@SuppressWarnings("unchecked")
	public List<String> getMembersRole(String token) {
		List<String> roleList = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role",
				ArrayList.class);
		return roleList;
	}

	// header에 저장한 access-token 반환
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

	public boolean validateRefreshTokenMatcheAccessTokenIssuer(String jwtToken, UUID userUniqId) {
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

	public boolean validateToken(String jwtToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return !claims.getBody().getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	public boolean validateTokenExceptExpiration(String jwtToken) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
			return true;
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
