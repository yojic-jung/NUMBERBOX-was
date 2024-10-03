package com.kamcci.numberbox.app.domain.dto.member

/**
 * 회원 가입 - 기본
 */
data class MemberSignUpDto(
    // 이메일
    val email: String,
    // 비밀번호
    val password: String,
)