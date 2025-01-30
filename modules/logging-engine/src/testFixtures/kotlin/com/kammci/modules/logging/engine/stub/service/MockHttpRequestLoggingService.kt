package com.kammci.modules.logging.engine.stub.service

import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.service.RequestLoggingService
import java.util.*

/**
 * RequestLoggingService 스텁
 */
class MockHttpRequestLoggingService(private val throwException: Boolean) : RequestLoggingService {


    // Request 정보 로깅
    override fun logging(): HttpRequestLoggingDto {
        if (throwException) throw RuntimeException()
        return HttpRequestLoggingDto(
            memberId = UUID.randomUUID(),
            browser = "chrome",
            os = "safari",
            ip = "127.0.0.1",
            method = "GET",
            uri = "/test",
            reqBody = "",
        )
    }

}