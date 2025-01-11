package com.kamcci.modules.logging.control.service

import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto

/**
 * 응답 정보 로깅
 */
interface ResponseLoggingService {
    fun logging(): HttpResponseLoggingDto
}