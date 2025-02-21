package com.kamcci.modules.logging.engine.advice

import com.kammci.modules.logging.engine.mock.common.MockEventPublisher
import com.kammci.modules.logging.engine.mock.common.MockProceedingJoinPoint
import com.kammci.modules.logging.engine.mock.service.MockHttpRequestLoggingService
import com.kammci.modules.logging.engine.mock.service.MockHttpResponseLoggingService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ClientLoggingAspectTest {

    @Test
    fun `request 로깅 - 실패`() {
        // given
        // g1. request 예외 발생
        val reqLoggingService = MockHttpRequestLoggingService(true)
        val resLoggingService = MockHttpResponseLoggingService(false)
        val eventPublisher = MockEventPublisher(false)
        val joinPoint = MockProceedingJoinPoint(200)

        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }

    @Test
    fun `response 로깅 - 실패`() {
        // given
        val reqLoggingService = MockHttpRequestLoggingService(false)
        // g1. response 예외 발생 설정
        val resLoggingService = MockHttpResponseLoggingService(true)

        val eventPublisher = MockEventPublisher(false)
        val joinPoint = MockProceedingJoinPoint(200)
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }

    @Test
    fun `request, reponse 로깅 - 실패`() {
        // g1. request 예외 발생 설정
        val reqLoggingService = MockHttpRequestLoggingService(true)
        // g1. response 예외 발생 설정
        val resLoggingService = MockHttpResponseLoggingService(true)
        val eventPublisher = MockEventPublisher(false)
        val joinPoint = MockProceedingJoinPoint(200)

        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }


    @Test
    fun `request, reponse 로깅 - 성공`() {
        // given
        val reqLoggingService = MockHttpRequestLoggingService(false)
        val resLoggingService = MockHttpResponseLoggingService(false)
        val eventPublisher = MockEventPublisher(false)
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)
        val joinPoint = MockProceedingJoinPoint(200)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isEqualTo(true)
    }

    @Test
    fun `request, reponse 로깅 - 실패(event 발행 중 예외 발생)`() {
        // given
        val reqLoggingService = MockHttpRequestLoggingService(false)
        val resLoggingService = MockHttpResponseLoggingService(false)
        // g1. eventPublisher 예외 발생
        val eventPublisher = MockEventPublisher(true)
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)
        val joinPoint = MockProceedingJoinPoint(200)

        // when & then
        assertDoesNotThrow {
            loggingAspect.logRequestAndResponse(joinPoint)
        }
    }
}

