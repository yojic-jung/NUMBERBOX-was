package com.kamcci.numberbox.members.restapi.controller;

import com.kamcci.numberbox.members.service.MembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

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
