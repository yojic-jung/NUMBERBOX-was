package com.kamcci.modules.logging.engine.service

import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.modules.logging.control.service.ResponseLoggingService
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

/**
 * 응답 정보 로깅
 */

@Service
class HttpResponseLoggingService : ResponseLoggingService {
    companion object {
        const val NOT_RESPONSE_ENTITY = "ResponseEntity 타입 반환값이 아닙니다."
    }

    override fun logging(returnValue: Any?): HttpResponseLoggingDto {
        // ResponseEntity 타입 검증
        if (returnValue !is ResponseEntity<*>) throw ClassCastException(NOT_RESPONSE_ENTITY)

        // 응답상태 코드 반환
        return HttpResponseLoggingDto(returnValue.statusCode.value())
    }
}