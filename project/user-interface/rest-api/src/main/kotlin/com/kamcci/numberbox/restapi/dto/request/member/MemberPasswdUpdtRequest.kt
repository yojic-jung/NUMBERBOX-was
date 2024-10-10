package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.exception.BusinessValidException
import com.kamcci.numberbox.restapi.validation.member.PasswordCheck

/**
 * 회원 비밀번호 변경 request
 */
data class MemberPasswdUpdtRequest(
    // 이전 비밀번호는 임시 비밀번호일 수 있으므로 유효성 검사 진행 안함
    val previousPassword: String,
    @field:PasswordCheck
    val password: String,
    val passwordConfirm: String
) {
    init {
        if (password != passwordConfirm) throw BusinessValidException("비밀번호와 비밀번호 확인 문자가 일치하지 않습니다.")
    }
}