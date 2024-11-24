package com.kamcci.numberbox.restapi.controller.members

import com.kamcci.modules.auth.control.annotation.UserEmail
import com.kamcci.modules.auth.control.annotation.UserId
import com.kamcci.numberbox.app.domain.dto.member.MemberPhoneUpdtDto
import com.kamcci.numberbox.app.domain.dto.member.MemberVerifyCodeDto
import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.app.usecase.member.MemberPrivateWriteUseCase
import com.kamcci.numberbox.app.usecase.member.MemberVerifyCodeReadUseCase
import com.kamcci.numberbox.restapi.dto.request.member.MemberPhoneUpdtRequest
import com.kamcci.numberbox.restapi.util.response.ResponseData
import com.kamcci.numberbox.restapi.util.response.ResponseUtil
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@PreAuthorize("hasRole('USER')")
@RestController
@RequestMapping("/member")
class MemberPrivateController(
    private val memberPrivateWriteUseCase: MemberPrivateWriteUseCase,
    private val memberVerifyCodeReadUseCase: MemberVerifyCodeReadUseCase,
) {
    // 휴대폰 번호 변경
    @PutMapping("/phone")
    fun updatePhoneNumber(
        @UserId memberId: UUID,
        @UserEmail email: String,
        @RequestBody @Valid
        req: MemberPhoneUpdtRequest
    ): ResponseEntity<ResponseData<Map<String, Any?>>> {
        // 1. 인증코드 검증
        val codeDto = MemberVerifyCodeDto(email, req.verifyCode, VerifyCodeType.PhoneNumber)
        memberVerifyCodeReadUseCase.validate(codeDto)

        // 2. 휴대폰 번호 변경
        val updtDto = MemberPhoneUpdtDto(memberId, req.phoneNumber)
        val isSuccess = memberPrivateWriteUseCase.updatePhoneNumber(updtDto)
        return ResponseUtil.ok(mapOf("isSuccess" to isSuccess))
    }
}