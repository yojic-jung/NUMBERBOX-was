package com.kamcci.numberbox.app.domain.dto.member

import java.time.LocalDateTime

data class MemberEmailVerifyCodeVo(
    val verifyCode: String,
    val sysCreateTime: LocalDateTime
)