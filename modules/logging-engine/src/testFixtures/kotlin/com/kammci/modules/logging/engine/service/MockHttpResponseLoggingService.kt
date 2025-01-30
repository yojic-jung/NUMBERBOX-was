package com.kammci.modules.logging.engine.service

import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.modules.logging.control.service.ResponseLoggingService

class MockHttpResponseLoggingService(val throwException: Boolean) : ResponseLoggingService {

    override fun logging(returnValue: Any?): HttpResponseLoggingDto {
        return if (throwException) throw RuntimeException() else HttpResponseLoggingDto(returnValue as Int)
    }
}