package com.kamcci.numberbox.app.domain.dto.member

import java.util.*

/**
 * 회원 탈퇴 dto
 */
data class MemberDropDto(
    val memberId: UUID,
    val verifyCode: UUID,
)