package com.numberbox.jwt.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import com.numberbox.jwt.service.ExpiredRefreshTokenService;
import com.numberbox.members.entity.MembersRole;
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
	  private ExpiredRefreshTokenService expiredRefreshTokenService;
	  @Autowired
	  private CustomSecurityUsersService customUsersService;
	
	  @Value("JIC727YO930SEC777TOKEN")
	  private String secretKey;
	
	  private final long ACCESS_TOKEN_VALID_TIME = 1000L * 60 * 60; //1시간
	  private final long REFRESH_TOKEN_VALID_TIME = 1000L * 60 * 60 * 24 * 30; // 1달
	
	  public String createAccessToken(String email, UUID userUniqId, List<MembersRole> roleList) {
		  List<String> strRoleList = new ArrayList<>();
		  for(MembersRole role : roleList) {
			  strRoleList.add(role.getRoleName());
		  }
		  
	      Claims claims = Jwts.claims().setSubject(email);
	      claims.put("userUniqId", userUniqId);
	      claims.put("role", strRoleList);
	      Date now = new Date();
	      return Jwts.builder()
	          .setClaims(claims)
	          .setIssuedAt(now)
	          .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
	          .signWith(SignatureAlgorithm.HS256, secretKey)
	          .compact();
	  }
	  
	  public String createAccessTokenRoleStr(String email, UUID userUniqId, List<String> roleList) {
	      Claims claims = Jwts.claims().setSubject(email);
	      claims.put("userUniqId", userUniqId);
	      claims.put("role", roleList);
	      Date now = new Date();
	      return Jwts.builder()
		          .setClaims(claims)
		          .setIssuedAt(now)
		          .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
		          .signWith(SignatureAlgorithm.HS256, secretKey)
		          .compact();
	  }
	
	  public String createRefreshToken(String email, UUID userUniqId) {
	      Claims claims = Jwts.claims();
	      Date now = new Date();
	      Date expiration = new Date(now.getTime() + REFRESH_TOKEN_VALID_TIME);
	
	      return Jwts.builder()
	          .setClaims(claims)
	          .setIssuedAt(now)
	          .setExpiration(expiration)
	          .signWith(SignatureAlgorithm.HS256, secretKey)
	          .compact();
	  }
	
	  public Authentication getAuthentication(String token) {
	      String email = getUserEmail(token);
	      UserDetails user = customUsersService.loadUserByUsername(email);
	      return new UsernamePasswordAuthenticationToken(user, "", user.getAuthorities());
	  }
	
	  public String getUserEmail(String token) {
	      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	  }
	  
	  public UUID getUserUniqId(String token) {
	      return (UUID)Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("userUniqId", UUID.class);
	  }
	  
	  @SuppressWarnings("unchecked")
	  public List<String> getMembersRole(String token) {
		  List<String> roleList = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("role", ArrayList.class);
	      return roleList;
	  }
	
	  //header에 저장한 access-token 반환
	  public String resolveAccessToken(HttpServletRequest request) {
	      String token = request.getHeader("access-token");
	      return token;
	  }
	
	  public String resolveRefreshToken(HttpServletRequest request) {
	      String token = null;
	      Cookie cookie = WebUtils.getCookie(request, "refresh-token");
	      if (cookie != null) token = cookie.getValue();
	      return token;
	  }
	
	  public boolean validateToken(String jwtToken) {
	      try {
	          Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
	          return !claims.getBody().getExpiration().before(new Date());
	      } catch (Exception e) {
	          return false;
	      }
	  }
	
	  public boolean validateRefreshToken(String jwtToken) {
	      if(expiredRefreshTokenService.isExpiredToken(jwtToken)) {
	          return false;
	      }
	
	      return validateToken(jwtToken);
	  }
	  
    public boolean validateTokenExceptExpiration(String jwtToken) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        } catch(ExpiredJwtException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
