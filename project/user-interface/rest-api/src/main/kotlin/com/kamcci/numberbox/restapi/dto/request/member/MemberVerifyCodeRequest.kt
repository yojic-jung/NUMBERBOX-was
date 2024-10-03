package com.kamcci.numberbox.restapi.dto.request.member

import java.util.*

/**
 * 회원 인증코드 request
 */
data class MemberVerifyCodeRequest(
    val verifyCode: UUID,
)