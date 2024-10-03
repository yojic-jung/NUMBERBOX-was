package com.kamcci.numberbox.app.domain.dto.member

import java.util.*

/**
 * 회원 휴대폰 번호 변경 dto
 */
data class MemberPhoneUpdtDto(
    val memberId: UUID,
    val verifyCode: UUID,
    val phoneNumber: String,
)