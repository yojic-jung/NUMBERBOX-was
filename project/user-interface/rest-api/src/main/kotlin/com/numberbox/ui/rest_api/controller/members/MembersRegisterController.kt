//package com.numberbox.ui.rest_api.controller.members
//
//import com.numberbox.members.appservice.usecase.MembersRegisterUseCase
//import com.numberbox.members.restapi.dto.request.MembersRequest
//import com.numberbox.members.restapi.dto.response.SignUpResultDto
//import com.numberbox.modules.auth.control.service.TokenResponseService
//import com.numberbox.ui.rest_api.util.response.ResponseData
//import org.springframework.http.ResponseEntity
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//class MembersRegisterController(
//    private val membersUseCase: MembersRegisterUseCase,
//    private val tokenResponseService: TokenResponseService
//) {
//
//    @PostMapping("/signup")
//    fun signup(members: MembersRequest): ResponseEntity<ResponseData<SignUpResultDto>> {
//        val signUpResult = membersUseCase.signUp(members)
//        tokenResponseService.createAndSetTokenToResponse(
//            signUpResult.email,
//            signUpResult.userUniqId,
//            signUpResult.roles
//        )
//        return ResponseUtil.ok(signUpResult)
//    }
//}
//
