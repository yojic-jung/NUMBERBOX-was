package com.kamcci.numberbox.app.domain.dto.member

/**
 * 회원 가입 - 개인정보
 */
data class MemberPrivateSignUpDto(
    val userName: String,
    val phoneNumber: String,
    val birth: String,
)