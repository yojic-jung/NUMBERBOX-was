package com.kamcci.numberbox.app.domain.dto.member

import java.util.*

/**
 * 회원 비밀번호 검증 dto
 */
data class MemberPasswdConfirmDto(
    val memberId: UUID,
    val password: String,
)