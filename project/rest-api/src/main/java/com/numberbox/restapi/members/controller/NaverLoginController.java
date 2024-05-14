package com.numberbox.restapi.members.controller;

import com.numberbox.auth.control.service.TokenResponseService;
import com.numberbox.members.appservice.usecase.MembersLoginUseCase;
import com.numberbox.members.restapi.dto.request.MembersRequest;
import com.numberbox.members.restapi.dto.response.SignUpResultDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import template.ResponseData;
import util.ResponseUtil;

@RestController
public class NaverLoginController {
    @Qualifier("naver")
    private final MembersLoginUseCase membersLoginUseCase;
    private final TokenResponseService tokenResponseService;

    public NaverLoginController(MembersLoginUseCase membersLoginUseCase, TokenResponseService tokenResponseService) {
        this.membersLoginUseCase = membersLoginUseCase;
        this.tokenResponseService = tokenResponseService;
    }

    @PostMapping("/naverLogin")
    public ResponseEntity<ResponseData<SignUpResultDto>> naverLogin(String email) {
        // todo 테스트 필요 and email 외의 다른 인증 키값도 보안상 필요
        SignUpResultDto signUpResultDto = membersLoginUseCase.login(email);
        tokenResponseService.createAndSetTokenToResponse(signUpResultDto.getEmail(),
                signUpResultDto.getUserUniqId(), signUpResultDto.getRoles());
        return ResponseUtil.ok(signUpResultDto);
    }
}
