package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.app.usecase.member.*
import com.kamcci.numberbox.restapi.dto.request.member.EmailFindRequest
import com.kamcci.numberbox.restapi.dto.request.member.EmailRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import com.kamcci.numberbox.restapi.validation.member.EmailCheck
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/public/member")
class MemberPublicController(
    private val memberWriteUseCase: MemberWriteUseCase,
    private val memberReadUseCase: MemberReadUseCase,
    private val memberVerifyCodeWriteUseCase: MemberVerifyCodeWriteUseCase,
    private val memberFindUseCase: MemberFindUseCase,
    private val memberVerifyCodeReadUseCase: MemberVerifyCodeReadUseCase,
    private val tokenResponseService: TokenResponseService,
    private val memberMapper: MemberMapper,
) {
    /**
     * 회원가입 목적 인증 코드 발급
     */
    @PostMapping("/signup/verifyCode")
    fun createVerifyCode(
        @Valid
        @RequestBody req: EmailRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        // 이메일 중복 체크
        val isExist = memberReadUseCase.existEmail(req.email)
        if (isExist) throw BusinessValidException("해당 이메일이 이미 존재합니다.")

        // 인증 코드 생성
        memberVerifyCodeWriteUseCase.createVerifyCode(req.email, req.codeType)
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
        memberVerifyCodeReadUseCase.validate(codeDto)

        // 2. 회원가입
        val resultVo = memberWriteUseCase.signup(memberSignUpDto, memberPrivateSignupDto)

        // 성공시 인증 토큰 반환
        tokenResponseService.responseAuthToken(resultVo.email, resultVo.uuid, resultVo.roles)
        return ResponseUtil.ok(mapOf("resultVo" to resultVo))
    }

    /**
     * 이메일 찾기
     */
    @GetMapping("/findEmail")
    fun findEmail(
        @Valid
        @ModelAttribute req: EmailFindRequest
    ): ResponseEntity<ResponseData<Map<String, String?>>> {
        return ResponseUtil.ok(mapOf("email" to memberFindUseCase.readMyEmail(req.userName, req.phoneNumber)))
    }

    /**
     * 비밀번호 찾기
     */
    @GetMapping("/findPassword")
    fun findPassword(
        @EmailCheck
        @RequestParam email: String
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberFindUseCase.readMyPassword(email)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }
}