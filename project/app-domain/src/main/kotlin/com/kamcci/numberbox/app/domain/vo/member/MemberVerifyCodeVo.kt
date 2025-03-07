package com.kamcci.numberbox.app.domain.vo.member

import java.time.LocalDateTime

/**
 * 회원 인증코드 정보
 */
data class MemberVerifyCodeVo(
    val verifyCode: String,
    val sysCreateTime: LocalDateTime
)