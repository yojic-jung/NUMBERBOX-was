package com.kamcci.numberbox.restapi.dto.request.member

import com.kamcci.numberbox.app.domain.enumeration.member.VerifyCodeType
import com.kamcci.numberbox.restapi.validation.member.EmailCheck

/**
 * 회원가입 목적 인증 코드 요청
 */
data class EmailRequest(
    @field:EmailCheck
    val email: String,
    val codeType: VerifyCodeType
)