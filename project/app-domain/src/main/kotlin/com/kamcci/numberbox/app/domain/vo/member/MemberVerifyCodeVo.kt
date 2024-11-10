package com.kamcci.numberbox.app.domain.vo.member

import java.time.LocalDateTime

data class MemberVerifyCodeVo(
    val verifyCode: String,
    val sysCreateTime: LocalDateTime
)