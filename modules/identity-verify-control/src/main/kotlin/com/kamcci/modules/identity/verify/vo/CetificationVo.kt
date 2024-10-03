package com.kamcci.modules.identity.verify.vo

import java.util.*

/**
 * 본인인증 정보
 */
data class CetificationVo(
    // 인증 여부
    val isCertified: Boolean,
    // 성별
    val gender: String,
    // 생년월일
    val birth: Date,
    // 휴대폰 번호
    val phone: String,
    // 인증 날짜
    val certifiedAt: Date,
)