package com.kamcci.modules.logging.control.dto

/**
 * 클라이언트 http 요청 및 응답 로깅 정보
 */
data class ClientLoggingInfoEventDto(
    val reqLoggingDto: HttpRequestLoggingDto,
    val resLoggingDto: HttpResponseLoggingDto?,
)