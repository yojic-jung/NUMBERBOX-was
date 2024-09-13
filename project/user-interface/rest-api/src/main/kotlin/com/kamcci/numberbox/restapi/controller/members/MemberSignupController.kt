package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import com.kamcci.numberbox.restapi.dto.request.EmailRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Validated
@RestController
@RequestMapping("/public")
class MemberSignupController(
//    private val membersUseCase: MembersRegisterUseCase,
//    private val tokenResponseService: TokenResponseService
    private val memberSignupUseCase: MemberSignupUseCase,
) {
    @PostMapping("/createEmailIdCode")
    fun createEmailVerifyCode(
        @Valid
        @RequestBody req: EmailRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberSignupUseCase.createEmailCode(req.email)
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
