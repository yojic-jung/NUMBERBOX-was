package com.kamcci.modules.logging.control.service

import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto

/**
 * 요청 정보 로깅
 */
interface RequestLoggingService {

    fun logging(): HttpRequestLoggingDto?
}