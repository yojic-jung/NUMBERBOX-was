package com.numberbox.members.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.service.MembersService;

@RestController
public class MembersController {
	
	@Autowired
	MembersService membersService;
	
	/*
	@PostMapping("/login")
	public HashMap<String, Object> login(@ModelAttribute MembersDto membersDto, HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> map = membersService.login(membersDto, request);
		Cookie refreshTokenCookie = new Cookie("refresh-token", (String)map.get("refreshToken"));
        String loginState = (String)request.getParameter("loginState");
        if(loginState !=null && loginState.equals("keep")) {
        	refreshTokenCookie.setMaxAge(60*60*24*60);
        }
        response.setHeader("access-token", (String)map.get("accessToken"));
        response.addCookie(refreshTokenCookie);
        
        HashMap<String, Object> returnMap = new  HashMap<>();
        returnMap.put("isLogin", map.get("isLogin"));	
		return returnMap;
	}
	*/
	
	@PostMapping("/loginSuccess")
	public HashMap<String, Object> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
		String refreshToken = (String)request.getAttribute("refreshToken");
		String loginState = (String)request.getAttribute("loginState");
		Cookie refreshTokenCookie = new Cookie("refresh-token", refreshToken);
		if(loginState !=null && loginState.equals("keep")) {
        	refreshTokenCookie.setMaxAge(60*60*24*60);
        }
		
		response.addCookie(refreshTokenCookie);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isLogin", true);
		return map;
	}
	
	
	@PostMapping("/loginFail")
	public HashMap<String, Object> loginFailure(HttpServletRequest request, HttpServletResponse response) {
		String customErrMsg = (String)request.getAttribute("customErrMsg");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("isSuccess", false);
		map.put("customErrMsg", customErrMsg);
		return map;
	}
	
	@PostMapping("/signup")
	public HashMap<String, Object> signup(MembersDto members, HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> returnMap = membersService.signUp(members);
		String isSuccess = returnMap.get("isSuccess");
		if(isSuccess.equals("success")) {
			Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
			response.setHeader("access-token", returnMap.get("accessToken"));
	        response.addCookie(refreshTokenCookie);
		}
		map.put("isSuccess", isSuccess);
		return map;
	}
	
	
	@PostMapping("/accessDenied")
	public HashMap<String, Object> accessDenied(HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("existMsg", true);
		map.put("serverMsg", "해당 요청에 접근 권한이 없습니다.");
		return map;
	}
	
	
	
}
