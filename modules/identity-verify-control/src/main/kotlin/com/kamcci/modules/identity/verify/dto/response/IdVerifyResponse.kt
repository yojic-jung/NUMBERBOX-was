package com.kamcci.modules.identity.verify.dto.response

/**
 * 본인인증 응답 템플릿
 */
data class IdVerifyResponse<T>(
    // http 상태코드
    val code: Int,
    // 응답 메시지
    val message: String,
    // 응답 객체
    val response: T,
)