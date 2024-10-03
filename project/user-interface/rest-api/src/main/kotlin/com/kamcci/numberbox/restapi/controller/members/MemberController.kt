package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.modules.auth.control.service.TokenResponseService
import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadUseCase
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberSignupRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberVerifyCodeRequest
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
    private val memberVerifyCodeReadUseCase: MemberVerifyCodeReadUseCase,
    private val tokenResponseService: TokenResponseService,
    private val memberMapper: MemberMapper,
) {
    // 내 이메일
    @GetMapping("/email")
    fun email(@UserEmail email: String): ResponseEntity<ResponseData<Map<String, Any?>>> {
        return ResponseUtil.ok(mapOf("email" to email))
    }

    // 비밀번호 변경
    @PutMapping("/password")
    fun updatePassword(
        @UserId memberId: UUID,
        @UserEmail email: String,
        @RequestBody @Valid req: MemberPasswdUpdtRequest
    ): ResponseEntity<ResponseData<Any>> {
        // 1. 인증코드 검증
        val codeDto = MemberVerifyCodeDto(email, req.verifyCode, VerifyCodeType.Password)
        val verifyCodeRs = memberVerifyCodeReadUseCase.validate(codeDto)
        if (!verifyCodeRs.isSuccess) {
            return ResponseUtil.ok(
                mapOf(
                    "isSuccess" to verifyCodeRs.isSuccess,
                    "verifyCodeResult" to verifyCodeRs
                )
            )
        }

        // 2. 비밀번호 변경
        val updtDto = memberMapper.toPasswdUpdtDto(memberId, req)
        val isSuccess = memberModifyUseCase.updatePassword(updtDto)

        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }


    // 회원가입
    @PostMapping("/public/signUp")
    fun signup(
        @RequestBody @Valid req: MemberSignupRequest
    ): ResponseEntity<ResponseData<Any>> {
        val memberSignUpDto = memberMapper.toSignupDto(req)
        val memberPrivateSignupDto = memberMapper.toSignupPrivateDto(req.privateInfo)

        // [회원가입 진행]
        // 1. 인증코드 검증
        val codeDto = MemberVerifyCodeDto(req.email, req.emailVerifyCode, VerifyCodeType.SignUp)
        val verifyCodeRs = memberVerifyCodeReadUseCase.validate(codeDto)
        if (!verifyCodeRs.isSuccess) {
            return ResponseUtil.ok(
                mapOf(
                    "isSuccess" to verifyCodeRs.isSuccess,
                    "verifyCodeResult" to verifyCodeRs
                )
            )
        }
        
        // 2. 회원가입
        val resultVo = memberModifyUseCase.signup(memberSignUpDto, memberPrivateSignupDto)

        // 성공시 인증 토큰 반환
        if (resultVo.isSuccess) {
            tokenResponseService.responseAuthToken(resultVo.email, resultVo.uuid, resultVo.roles)
        }
        return ResponseUtil.ok(
            mapOf(
                "isSuccess" to resultVo.isSuccess,
                "resultVo" to resultVo
            )
        )
    }

    // 탈퇴
    @PostMapping("/drop")
    fun dropAccount(
        @UserId memberId: UUID,
        @RequestBody @Valid verifyCodeRequest: MemberVerifyCodeRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        val dropDto = memberMapper.toDropDto(memberId, verifyCodeRequest)
        val isSuccess = memberModifyUseCase.drop(dropDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }
}