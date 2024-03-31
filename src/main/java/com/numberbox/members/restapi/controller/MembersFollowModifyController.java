package com.numberbox.members.restapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.numberbox.members.service.MembersService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MembersFollowModifyController {
    private final MembersService membersService;

    public MembersFollowModifyController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping("/followingCancel")
    public Map<String, Object> followingCancel(@RequestParam int userNo) throws IllegalStateException {
        return membersService.followingCancel(userNo);
    }
}
