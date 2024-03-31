package com.numberbox.members.restapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.members.dto.MembersDto;
import com.numberbox.members.dto.PasswordModel;
import com.numberbox.members.service.MembersService;

@RestController
public class MembersModifyController {

	private MembersService membersService;

	public MembersModifyController(MembersService membersService) {
		this.membersService = membersService;
	}

	@PostMapping(value = "/changePassword")
	public Map<String, Object> changePassword(PasswordModel passwordModel) {
		return membersService.changePassword(passwordModel);
	}

	@PostMapping(value = "/changePhoneNumber")
	public Map<String, Object> changePhoneNumber(MembersDto membersDto) {
		return membersService.changePhoneNumber(membersDto);
	}

	@PostMapping(value = "/myAccountDrop")
	public Map<String, Object> myAccountDrop(MembersDto membersDto) {
		return membersService.myAccountDrop(membersDto);
	}

}
