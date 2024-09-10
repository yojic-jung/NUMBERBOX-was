package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberSignupController(
//    private val membersUseCase: MembersRegisterUseCase,
//    private val tokenResponseService: TokenResponseService
    private val memberSignupUseCase: MemberSignupUseCase,
) {
    @GetMapping("/createEmailIdCode")
    fun createEmailVerifyCode(@RequestParam email: String): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberSignupUseCase.createEmailCode(email)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }

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
}
