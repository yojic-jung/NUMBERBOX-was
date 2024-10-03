package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.vo.member.MemberSignUpResultVo
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeSaveUseCase
import com.kamcci.numberbox.restapi.dto.request.member.*
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberModifyUseCase: MemberModifyUseCase,
    private val memberVerifyCodeSaveUseCase: MemberVerifyCodeSaveUseCase,
    private val tokenResponseService: TokenResponseService,
    private val memberMapper: MemberMapper,
) {
    // 내 이메일
    @GetMapping("/email")
    fun email(@UserEmail email: String): ResponseEntity<ResponseData<Map<String, Any?>>> {
        return ResponseUtil.ok(mapOf("email" to email))
    }

    @PostMapping("/createVerifyCode")
    fun createEmailVerifyCode(
        @Valid
        @RequestBody req: EmailRequest
    ): ResponseEntity<ResponseData<Map<String, Boolean>>> {
        memberVerifyCodeSaveUseCase.createVerifyCode(req.email, req.codeType)
        return ResponseUtil.ok(mapOf("isSuccess" to true))
    }

    // 비밀번호 변경
    @PutMapping("/password")
    fun updatePassword(
        @UserId
        memberId: UUID,
        @RequestBody @Valid
        passwordUpdtReq: MemberPasswdUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val updtDto = memberMapper.toPasswdUpdtDto(memberId, passwordUpdtReq)
        val isSuccess = memberModifyUseCase.updatePassword(updtDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }

    // 휴대폰 번호 변경
    @PutMapping("/phone")
    fun updatePhoneNumber(
        @UserId
        memberId: UUID,
        @RequestBody @Valid
        phoneUpdtDto: MemberPhoneUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val updtDto = memberMapper.toPhoneUpdtDto(memberId, phoneUpdtDto)
        val isSuccess = memberModifyUseCase.updatePhoneNumber(updtDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }

    @PostMapping("/signUp")
    fun signup(
        @Valid
        @RequestBody
        req: MemberSignupRequest
    ): ResponseEntity<ResponseData<MemberSignUpResultVo>> {
        val memberSignUpDto = memberMapper.toSignupDto(req)
        val memberPrivateSignupDto = memberMapper.toSignupPrivateDto(req.privateInfo)

        // 회원가입 진행
        val resultVo = memberModifyUseCase.signup(memberSignUpDto, memberPrivateSignupDto)

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

    // 탈퇴
    @PostMapping("/drop")
    fun dropAccount(
        @UserId
        memberId: UUID,
        @RequestBody @Valid
        verifyCodeRequest: MemberVerifyCodeRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val dropDto = memberMapper.toDropDto(memberId, verifyCodeRequest)
        val isSuccess = memberModifyUseCase.drop(dropDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }
}