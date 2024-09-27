package com.kamcci.numberbox.app.domain.vo.member

import java.time.LocalDateTime

data class MemberEmailVerifyCodeVo(
    val verifyCode: String,
    val sysCreateTime: LocalDateTime
)