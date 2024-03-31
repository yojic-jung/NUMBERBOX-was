package com.numberbox.members.restapi.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.service.MembersService;

@RestController
public class MembersRegisterController {

	private MembersService membersService;

	public MembersRegisterController(MembersService membersService) {
		this.membersService = membersService;
	}

	@PostMapping("/signup")
	public HashMap<String, Object> signup(HttpServletRequest request, MembersDto members,
			HttpServletResponse response) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		HashMap<String, String> returnMap = membersService.signUp(request, members);
		returnMap.put("isSuccess", "not");
		String isSuccess = returnMap.get("isSuccess");
		if (isSuccess.equals("success")) {
			Cookie refreshTokenCookie = new Cookie("refresh-token", returnMap.get("refreshToken"));
			response.setHeader("access-token", returnMap.get("accessToken"));
			refreshTokenCookie.setPath("/"); // context-path를 myWasApi로 설정하면서 쿠키 Path가 /myWasApi로 바뀜 다시 / 루트 컨텐스트로 쿠키 패쓰
												// 설정
			refreshTokenCookie.setMaxAge(60 * 60 * 6); // 6시간
			refreshTokenCookie.setHttpOnly(true);
			refreshTokenCookie.setSecure(true);
			response.addCookie(refreshTokenCookie);
		}
		map.put("isSuccess", isSuccess);
		return map;
	}
}
