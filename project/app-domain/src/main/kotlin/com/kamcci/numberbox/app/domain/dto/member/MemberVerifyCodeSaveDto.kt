package com.kamcci.numberbox.app.domain.dto.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType

/**
 * 회원가입시 이메일 검증을 위한 id_code 영속화 목적 dto
 */
data class MemberVerifyCodeSaveDto(
    val email: String,
    val codeType: VerifyCodeType,
    val verifyCode: String
)