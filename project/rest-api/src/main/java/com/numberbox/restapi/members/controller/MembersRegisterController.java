//package com.numberbox.restapi.members.controller;
//
//import com.numberbox.auth.control.service.TokenResponseService;
//import com.numberbox.members.appservice.usecase.MembersRegisterUseCase;
//import com.numberbox.members.restapi.dto.request.MembersRequest;
//import com.numberbox.members.restapi.dto.response.SignUpResultDto;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import template.ResponseData;
//import util.ResponseUtil;
//
//@RestController
//public class MembersRegisterController {
//    private final MembersRegisterUseCase membersUseCase;
//    private final TokenResponseService tokenResponseService;
//
//    public MembersRegisterController(MembersRegisterUseCase membersUseCase, TokenResponseService tokenResponseService) {
//        this.membersUseCase = membersUseCase;
//        this.tokenResponseService = tokenResponseService;
//    }
//
//    @PostMapping("/signup")
//    public ResponseEntity<ResponseData<SignUpResultDto>> signup(MembersRequest members) {
//        SignUpResultDto signUpResult = membersUseCase.signUp(members);
//        tokenResponseService.createAndSetTokenToResponse(signUpResult.getEmail(),
//                signUpResult.getUserUniqId(),
//                signUpResult.getRoles());
//        return ResponseUtil.ok(signUpResult);
//    }
//}
