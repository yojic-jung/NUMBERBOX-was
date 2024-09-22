package com.kamcci.numberbox.app.domain.dto.member

/**
 * 회원가입시 이메일 검증을 위한 id_code 영속화 목적 dto
 */
data class MemberEmailVerifyCodeSaveDto(
    val email: String,
    val verifyCode: String
)