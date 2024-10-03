package com.kamcci.modules.identity.verify.vo

/**
 * 본인인증 서비스 상점 정보
 */
data class IdVerifyMerchantVo(
    val merchantUid: String,
    val merchantIdCode: String
)