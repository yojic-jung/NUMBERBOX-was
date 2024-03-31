package com.numberbox.members.restapi.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.numberbox.members.service.MembersService;

public class MembersFollowReadController {

	private MembersService membersService;

	public MembersFollowReadController(MembersService membersService) {
		this.membersService = membersService;
	}

	@GetMapping("/followingUser")
	public HashMap<String, Object> followingUser(@RequestParam int userNo, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException {
		HashMap<String, Object> map = membersService.followingUser(userNo);
		return map;
	}
}
