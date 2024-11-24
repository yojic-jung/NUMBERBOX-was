package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.member.MemberPasswdConfirmDto
import com.kamcci.numberbox.app.usecase.member.MemberWriteCase
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdConfirmRequest
import com.kamcci.numberbox.restapi.dto.request.member.MemberPasswdUpdtRequest
import com.kamcci.numberbox.restapi.mapper.member.MemberMapper
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member")
class MemberWriteController(
    private val memberWriteCase: MemberWriteCase,
    private val memberMapper: MemberMapper,
) {
    // 비밀번호 변경
    @PutMapping("/password")
    fun updatePassword(
        @UserId memberId: UUID,
        @UserEmail email: String,
        @RequestBody @Valid req: MemberPasswdUpdtRequest
    ): ResponseEntity<ResponseData<Any>> {
        val updtDto = memberMapper.toPasswdUpdtDto(memberId, req)
        val isSuccess = memberWriteCase.updatePassword(updtDto)

        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }

    // 비밀번호 확인(변경 작업 없지만 개인정보 post로 받음)
    @PostMapping("/password-confirm")
    fun confirmPassword(
        @UserId memberId: UUID,
        @RequestBody @Valid req: MemberPasswdConfirmRequest
    ): ResponseEntity<ResponseData<Any>> {
        val confirmDto = MemberPasswdConfirmDto(memberId, req.password)
        val isSuccess = memberWriteCase.confirmPassword(confirmDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }

    // todo
    // 탈퇴
//    @PostMapping("/drop")
//    fun dropAccount(
//        @UserId memberId: UUID,
//        @RequestBody @Valid verifyCodeRequest: MemberVerifyCodeRequest
//    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
//        val dropDto = memberMapper.toDropDto(memberId, verifyCodeRequest)
//        val isSuccess = memberModifyUseCase.drop(dropDto)
//        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
//    }
}