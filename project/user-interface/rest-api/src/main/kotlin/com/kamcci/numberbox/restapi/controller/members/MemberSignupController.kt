package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.usecase.member.MemberSignupUseCase
import com.kamcci.numberbox.restapi.dto.request.member.EmailRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberSignupMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public")
class MemberSignupController(
    private val tokenResponseService: TokenResponseService,
    private val signupUseCase: MemberSignupUseCase,
    private val signupMapper: MemberSignupMapper,
) {
    @PostMapping("/createEmailIdCode")
    fun createEmailVerifyCode(
        @Valid
        @RequestBody req: EmailRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        signupUseCase.createEmailCode(req.email)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }

    @PostMapping("/signUp")
    fun signup(
        @Valid
        @RequestBody
        req: MemberSignupRequest
    ): ResponseEntity<ResponseData<MemberSignUpResultVo>> {
        val memberSignUpDto = signupMapper.toDto(req)
        val memberPrivateSignupDto = signupMapper.toPrivateDto(req.privateInfo)

        // 회원가입 진행
        val resultVo = signupUseCase.signup(memberSignUpDto, memberPrivateSignupDto)

        // 성공시 인증 토큰 반환
        if (resultVo.isSuccess) {
            tokenResponseService.responseAuthToken(
                resultVo.email,
                resultVo.uuid,
                resultVo.roles
            )
        }

        return ResponseUtil.ok(resultVo)
    }
}
