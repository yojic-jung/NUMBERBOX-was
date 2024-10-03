package com.kamcci.numberbox.app.domain.dto.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import java.util.*

/**
 * 인증 코드 검증 목적 dto
 */
data class MemberVerifyCodeDto(
    val email: String,
    val verifyCode: UUID,
    val verifyCodeType: VerifyCodeType
)