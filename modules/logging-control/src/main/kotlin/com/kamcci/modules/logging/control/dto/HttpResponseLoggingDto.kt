package com.kamcci.modules.logging.control.dto

/**
 * 클라이언트 http 요청에 대한 서버 응답 로깅 정보
 */
data class HttpResponseLoggingDto(
    val httpStatus: Int
)