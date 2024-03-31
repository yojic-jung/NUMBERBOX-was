package com.numberbox.members.restapi.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.numberbox.members.service.MembersService;

public class MembersFollowReadController {

    private final MembersService membersService;

    public MembersFollowReadController(MembersService membersService) {
        this.membersService = membersService;
    }

    @GetMapping("/followingUser")
    public Map<String, Object> followingUser(@RequestParam int userNo) throws IllegalStateException {
        return membersService.followingUser(userNo);
    }
}
