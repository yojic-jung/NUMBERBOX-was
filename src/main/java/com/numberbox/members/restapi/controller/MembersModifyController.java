package com.numberbox.members.restapi.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.request.PasswordRequest;
import com.numberbox.members.service.MembersService;

@RestController
public class MembersModifyController {

    private final MembersService membersService;

    public MembersModifyController(MembersService membersService) {
        this.membersService = membersService;
    }

    @PostMapping(value = "/changePassword")
    public Map<String, Object> changePassword(PasswordRequest passwordRequest) {
        return membersService.changePassword(passwordRequest);
    }

    @PostMapping(value = "/changePhoneNumber")
    public Map<String, Object> changePhoneNumber(MembersRequest membersRequest) {
        return membersService.changePhoneNumber(membersRequest);
    }

    @PostMapping(value = "/myAccountDrop")
    public Map<String, Object> myAccountDrop(MembersRequest membersRequest) {
        return membersService.myAccountDrop(membersRequest);
    }

}
