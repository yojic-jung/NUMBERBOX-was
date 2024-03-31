package com.numberbox.members.restapi.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.members.dto.MembersProfileDto;
import com.numberbox.members.service.MembersService;

@RestController
public class MembersProfileModifyController {

	private MembersService membersService;

	public MembersProfileModifyController(MembersService membersService) {
		this.membersService = membersService;
	}

	@GetMapping(value = "/registerMemberProfile")
	public Map<String, Object> registerMemberProfile(HttpServletRequest request) {
		String profileType = request.getParameter("profileType");
		return membersService.registerMemberProfile(Integer.parseInt(profileType));
	}

	@PostMapping("/registerProfileImg")
	public Map<String, Object> registerProfileImg(@ModelAttribute MembersProfileDto membersProfileDto,
			HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException {
		String path = request.getSession().getServletContext().getRealPath("/static"); // 임시용, 배포 이후 프로젝트 바깥 경로로 설정하는게
																						// 좋음(배포용 개발용 따로 관리 필요)
		return membersService.registerProfileImg(membersProfileDto, path);
	}

	@GetMapping("/changeNickname")
	public Map<String, Object> changeNickname(@RequestParam String nickname, HttpServletRequest request,
			HttpServletResponse response) throws IllegalStateException, IOException {
		return membersService.changeNickname(nickname);
	}

}
