package com.kamcci.modules.logging.engine.service

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class HttpResponseLoggingServiceTest {
    private val responseLoggingService = HttpResponseLoggingService()

    @Test
    fun `응답 정보 로깅 - 실패(ResponseEntity 타입 아님)`() {
        // given - 문자열 전달
        val notResponseEntity = "any"

        // when & then
        assertThrows<ClassCastException> {
            responseLoggingService.logging(notResponseEntity)
        }
    }

    @Test
    fun `응답 정보 로깅 - 성공`() {
        // given - ResponseEntity 전달
        val statusCode = HttpStatus.OK
        val responseEntity: ResponseEntity<String> = ResponseEntity(statusCode)

        // when
        val loggingDto = responseLoggingService.logging(responseEntity)

        // then
        assertThat(loggingDto.httpStatus).isEqualTo(statusCode.value())
    }
}