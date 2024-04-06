package com.numberbox.members.restapi.controller;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.service.MembersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class MembersProfileReadController {

    private final MembersService membersService;

    public MembersProfileReadController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping("/takeProfile")
    public Map<String, Object> takeProfile(HttpServletRequest request, HttpServletResponse response) {
        return membersService.takeProfile();
    }

    @GetMapping("/takeUserProfile")
    public Map<String, Object> takeUserProfile(@RequestParam int userNo, HttpServletRequest request, HttpServletResponse response) {
        return membersService.takeUserProfile(userNo);
    }

    @PostMapping(value = "/confirmPassword")
    public Map<String, Object> confirmPassword(MembersRequest memberDto) {
        return membersService.confirmPassword(memberDto.getPassword());
    }
}
