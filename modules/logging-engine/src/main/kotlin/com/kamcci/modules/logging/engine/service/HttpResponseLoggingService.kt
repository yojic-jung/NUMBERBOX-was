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
    override fun logging(returnValue: Any?): HttpResponseLoggingDto {
        // repsponse 추출
        val resEntity = returnValue as? ResponseEntity<*>

        // 응답상태 코드 반환
        return HttpResponseLoggingDto(resEntity?.statusCode?.value())
    }
}