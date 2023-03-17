package com.numberbox.security.handler;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.numberbox.jwt.service.ExpiredRefreshTokenService;
import com.numberbox.jwt.util.JwtUtil;
import com.numberbox.members.entity.Members;
import com.numberbox.members.entity.MembersRole;
import com.numberbox.members.repository.MembersRepository;
import com.numberbox.security.dto.CustomSecurityUser;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired 
	private JwtUtil jwtUtil;
	@Autowired 
	private ExpiredRefreshTokenService expiredRefreshTokenService;
	@Autowired
	private MembersRepository membersRepository;
	
    @Transactional(rollbackFor= {Exception.class})
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    	CustomSecurityUser user = null;
        if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            user = (CustomSecurityUser)authentication.getPrincipal();
        }
        String expiredToken = jwtUtil.resolveRefreshToken(request);
        //로그인시 클라이언트단에 refresh토큰이 남아있는 경우 해당 refresh토큰을 만료시킴(클라이언트단에 로그아웃시 refresh토큰 삭제하여 정상적인 로직시 해당 로직 타는 경우 없지만 refresh토큰 탈취하여 사용하는 경우 만료시킴 )
        if (expiredToken != null && !expiredToken.isEmpty()) {
            expiredRefreshTokenService.addExpiredToken(expiredToken);
        }
        
        Members members = user.getMembers();
        
        membersRepository.initLastLoginDate(members.getUserUniqId());
        membersRepository.initHumanStatus(members.getUserUniqId());
        
        //매니저 권한 임시 구현
        boolean isManager = false;
        boolean isAdmin = false;
        for(MembersRole role : members.getRole()) {
        	if(role.getRoleName().equals("MANAGER")) {
        		isManager=true;
        	}
        	else if(role.getRoleName().equals("ADMIN")) {
        		isAdmin=true;
        	}
        }
        
        if(isAdmin) {
        	response.setHeader("role", "ADMIN");
        }else if(!isAdmin && isManager) {
        	response.setHeader("role", "MANAGER");
        }else {
     	   response.setHeader("role", "USER");
        }
        
        String accessToken = jwtUtil.createAccessToken(request, members.getEmail(), members.getUserUniqId(), members.getRole());
        String refreshToken = jwtUtil.createRefreshToken(members.getEmail(), members.getUserUniqId());
        String loginState = (String)request.getParameter("loginState");
        response.setHeader("access-token", accessToken);
        request.setAttribute("refreshToken", refreshToken);
        request.setAttribute("loginState", loginState);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/loginSuccess");
        dispatcher.forward(request, response);
    }
}
