package com.numberbox.members.restapi.controller;

import com.numberbox.members.service.MembersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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
