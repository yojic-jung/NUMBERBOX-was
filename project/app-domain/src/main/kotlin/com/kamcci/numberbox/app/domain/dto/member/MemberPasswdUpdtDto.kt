package com.kamcci.numberbox.app.domain.dto.member

import com.kamcci.numberbox.app.domain.exception.BusinessInValidException
import java.util.*

/**
 * 회원 비밀번호 변경 dto
 */
data class MemberPasswdUpdtDto(
    val memberId: UUID,
    val previousPassword: String,
    val password: String,
    val passwordConfirm: String
) {
    init {
        if (password != passwordConfirm) throw BusinessInValidException("비밀번호와 비밀번호 확인 문자가 일치하지 않습니다.")
    }
}