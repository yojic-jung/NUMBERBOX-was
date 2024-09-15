package com.kamcci.numberbox.app.domain.dto.member

import java.time.LocalDateTime
import java.util.*

data class EmailVerifyCodeVo(
    val verifyCode: UUID,
    val sysCreateTime: LocalDateTime
)