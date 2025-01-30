package com.kammci.modules.logging.engine.stub.service

import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.modules.logging.control.service.ResponseLoggingService

/**
 * ResponseLoggingService 스텁
 */
class MockHttpResponseLoggingService(private val throwException: Boolean) : ResponseLoggingService {

    override fun logging(returnValue: Any?): HttpResponseLoggingDto {
        return if (throwException) throw RuntimeException() else HttpResponseLoggingDto(returnValue as Int)
    }
}