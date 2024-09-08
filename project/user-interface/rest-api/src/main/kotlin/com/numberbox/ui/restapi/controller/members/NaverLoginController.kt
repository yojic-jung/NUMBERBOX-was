//package com.numberbox.restapi.members.controller
//
//import com.numberbox.members.appservice.usecase.MembersLoginUseCase
//import com.numberbox.members.restapi.dto.response.SignUpResultDto
//import com.numberbox.modules.auth.control.service.TokenResponseService
//import com.numberbox.ui.rest_api.util.response.ResponseData
//import com.numberbox.ui.rest_api.util.response.ResponseUtil
//import org.springframework.beans.factory.annotation.Qualifier
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class NaverLoginController(
//    @Qualifier("naver") private val membersLoginUseCase: MembersLoginUseCase,
//    private val tokenResponseService: TokenResponseService
//) {
//
//    @PostMapping("/naverLogin")
//    fun naverLogin(email: String): ResponseEntity<ResponseData<SignUpResultDto>> {
//        // todo 테스트 필요 and email 외의 다른 인증 키값도 보안상 필요
//        val signUpResultDto = membersLoginUseCase.login(email)
//        tokenResponseService.createAndSetTokenToResponse(
//            signUpResultDto.email,
//            signUpResultDto.userUniqId,
//            signUpResultDto.roles
//        )
//        return ResponseUtil.ok(signUpResultDto)
//    }
//}
//
