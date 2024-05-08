package com.numberbox.members.restapi.controller;

import com.numberbox.auth.control.service.TokenResponseService;
import com.numberbox.members.appservice.usecase.MembersAuthUseCase;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MembersRegisterController {

    private final MembersAuthUseCase membersUseCase;
    private final TokenResponseService tokenResponseService;

    public MembersRegisterController(MembersAuthUseCase membersUseCase, TokenResponseService tokenResponseService) {
        this.membersUseCase = membersUseCase;
        this.tokenResponseService = tokenResponseService;
    }

    @PostMapping("/signup")
    public Map<String, Object> signup(MembersRequest members) {
        Map<String, Object> returnMap = membersUseCase.signUp(members);

        tokenResponseService.createAndSetTokenToResponse();
        return returnMap;
    }
}
