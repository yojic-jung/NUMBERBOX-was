package com.kamcci.numberbox.app.domain.dto.member

import java.util.*

/**
 * 회원 가입 - 기본
 */
data class MemberSignUpDto(
    // 이메일
    val email: String,
    // 비밀번호
    val password: String,
    // 이메일 검증 코드
    val emailVerifyCode: UUID,
)