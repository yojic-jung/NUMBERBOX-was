package com.kamcci.modules.logging.control.service

import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto

/**
 * 응답 정보 로깅
 */
interface ResponseLoggingService {
    /**
     * 응답 정보를 로깅함
     *
     * @return returnValue 타깃 메서드 반환값 -> ResponseEntity 타입이어야 가능함
     */
    fun logging(returnValue: Any?): HttpResponseLoggingDto
}