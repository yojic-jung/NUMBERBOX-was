package com.kamcci.modules.logging.control.dto

import java.util.*

/**
 * 클라이언트 http 요청 로깅 정보
 */
data class HttpRequestLoggingDto(
    val memberId: UUID,
    val browser: String,
    val os: String,
    val ip: String,
    val method: String,
    val uri: String,
    val reqBody: String?
)