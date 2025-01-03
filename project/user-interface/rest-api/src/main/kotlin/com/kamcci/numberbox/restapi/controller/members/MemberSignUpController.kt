package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import com.kamcci.numberbox.app.usecase.member.MemberReadCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeWriteCase
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import com.kamcci.numberbox.restapi.dto.request.member.EmailRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/public/member")
class MemberSignUpController(
    private val memberWriteCase: MemberWriteCase,
    private val memberReadCase: MemberReadCase,
    private val memberVerifyCodeWriteCase: MemberVerifyCodeWriteCase,
    private val memberVerifyCodeReadCase: MemberVerifyCodeReadCase,
    private val tokenResponseService: TokenResponseService,
    private val memberMapper: MemberMapper,
) {
    companion object {
        const val ALREADY_EXIST_EMAIL = "해당 이메일이 이미 존재합니다."
    }

    /**
     * 회원가입 목적 인증 코드 발급
     */
    @PostMapping("/signup/verifyCode")
    fun createVerifyCode(
        @Valid
        @RequestBody req: EmailRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        // 이메일 중복 체크
        val isExist = memberReadCase.existEmail(req.email)
        if (isExist) throw BusinessInValidException(ALREADY_EXIST_EMAIL)

        // 인증 코드 생성
        memberVerifyCodeWriteCase.createVerifyCode(req.email, req.codeType)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signup(
        @RequestBody @Valid req: MemberSignupRequest
    ): ResponseEntity<ResponseData<Any>> {
        val memberSignUpDto = memberMapper.toSignupDto(req)
        val memberPrivateSignupDto = memberMapper.toSignupPrivateDto(req.privateInfo)

        // 1. 인증코드 검증
        val codeDto = MemberVerifyCodeDto(req.email, req.emailVerifyCode, VerifyCodeType.SignUp)
        memberVerifyCodeReadCase.validate(codeDto)

        // 2. 회원가입
        val resultVo = memberWriteCase.signup(memberSignUpDto, memberPrivateSignupDto)

        // 성공시 인증 토큰 반환
        tokenResponseService.responseAuthToken(resultVo.email, resultVo.uuid, resultVo.roles)
        return ResponseUtil.ok(mapOf("resultVo" to resultVo))
    }

}