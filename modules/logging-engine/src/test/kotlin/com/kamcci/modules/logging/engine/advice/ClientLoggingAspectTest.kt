package com.kamcci.modules.logging.engine.advice

import com.kammci.modules.logging.engine.mock.common.MockEventPublisher
import com.kammci.modules.logging.engine.mock.common.MockProceedingJoinPoint
import com.kammci.modules.logging.engine.mock.service.MockHttpRequestLoggingService
import com.kammci.modules.logging.engine.mock.service.MockHttpResponseLoggingService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

class ClientLoggingAspectTest {

    lateinit var reqLoggingService: MockHttpRequestLoggingService
    lateinit var resLoggingService: MockHttpResponseLoggingService
    lateinit var eventPublisher: MockEventPublisher
    lateinit var loggingAspect: ClientLoggingAspect

    // 테스트 더블 설정으로 인자값 true 설정시 해당 더블에서 예외 발생
    private fun setUpMock(
        isReqException: Boolean = false,
        isResException: Boolean = false,
        isEventException: Boolean = false
    ) {
        reqLoggingService = MockHttpRequestLoggingService(isReqException)
        resLoggingService = MockHttpResponseLoggingService(isResException)
        eventPublisher = MockEventPublisher(isEventException)

        loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)
    }

    @Test
    fun `request 로깅 - 실패`() {
        // given - request 예외 발생 설정
        setUpMock(isReqException = true)
        val joinPoint = MockProceedingJoinPoint(200)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }

    @Test
    fun `response 로깅 - 실패`() {
        // given - response 예외 발생 설정
        setUpMock(isResException = true)
        val joinPoint = MockProceedingJoinPoint(200)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }

    @Test
    fun `request, reponse 로깅 - 실패`() {
        // given - request, response 예외 발생 설정
        setUpMock(isReqException = true, isResException = true)
        val joinPoint = MockProceedingJoinPoint(200)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isFalse()
    }


    @Test
    fun `request, reponse 로깅 - 성공`() {
        // given
        setUpMock()
        val joinPoint = MockProceedingJoinPoint(200)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isRun).isEqualTo(true)
    }

    @Test
    fun `request, reponse 로깅 - 실패(event 발행 중 예외 발생)`() {
        // given - eventPublisher 예외 발생
        setUpMock(isEventException = true)
        val joinPoint = MockProceedingJoinPoint(200)

        // when & then
        assertDoesNotThrow {
            loggingAspect.logRequestAndResponse(joinPoint)
        }
    }
}

