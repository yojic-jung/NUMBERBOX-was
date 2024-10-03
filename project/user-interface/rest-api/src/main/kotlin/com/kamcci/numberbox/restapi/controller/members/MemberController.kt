package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.usecase.member.MemberModifyUseCase
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
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