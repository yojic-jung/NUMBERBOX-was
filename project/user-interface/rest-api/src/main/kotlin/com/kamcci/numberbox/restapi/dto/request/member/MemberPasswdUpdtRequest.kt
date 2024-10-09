package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.restapi.validation.member.PasswordCheck
import java.util.*

/**
 * 회원 비밀번호 변경 request
 */
data class MemberPasswdUpdtRequest(
    val verifyCode: UUID,
    @field:PasswordCheck
    val password: String,
    val passwordConfirm: String
) {
    init {
        if (password != passwordConfirm) throw BusinessValidException("비밀번호와 비밀번호 확인 문자가 일치하지 않습니다.")
    }
}