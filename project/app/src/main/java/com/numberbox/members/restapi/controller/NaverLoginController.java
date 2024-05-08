package com.numberbox.members.restapi.controller;

import com.numberbox.auth.control.service.TokenResponseService;
import com.numberbox.members.appservice.usecase.NaverLoginUseCase;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class NaverLoginController {
    private final NaverLoginUseCase naverLoginUseCase;
    private final TokenResponseService tokenResponseService;

    public NaverLoginController(NaverLoginUseCase naverLoginUseCase, TokenResponseService tokenResponseService) {
        this.naverLoginUseCase = naverLoginUseCase;
        this.tokenResponseService = tokenResponseService;
    }

    @PostMapping("/naverLogin")
    public Map<String, Object> naverLogin(MembersRequest members, HttpServletRequest request) {
        Map<String, Object> returnMap = naverLoginUseCase.naverLogin(members, request);
        tokenResponseService.createAndSetTokenToResponse();
        return returnMap;
    }

}
