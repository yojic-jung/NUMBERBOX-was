package com.numberbox.ui.rest_api.dto.request

import java.time.LocalDateTime

/**
 * 회원 가입 요청 dto
 */
data class MemberSignupRequest(
    // 이메일
    val email: String,
    // 비밀번호
    val password: String,
    // 비밀번호 확인
    val confirmPassword: Boolean,
    val userName: String,
    val phoneNumber: String,
    val birth: String,
    val signupDate: LocalDateTime,
    val lastLoginDate: LocalDateTime
)

