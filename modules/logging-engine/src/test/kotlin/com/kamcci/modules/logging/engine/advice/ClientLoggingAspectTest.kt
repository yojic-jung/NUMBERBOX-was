package com.kamcci.modules.logging.engine.advice

import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.modules.logging.control.service.RequestLoggingService
import com.kamcci.modules.logging.control.service.ResponseLoggingService
import com.kamcci.modules.logging.engine.service.HttpRequestLoggingService
import com.kamcci.modules.logging.engine.service.HttpResponseLoggingService
import com.kamcci.modules.logging.engine.util.IPAddressUtil
import org.aspectj.lang.ProceedingJoinPoint
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.context.ApplicationEventPublisher
import java.util.*

class ClientLoggingAspectTest {

    private val joinPoint: ProceedingJoinPoint = mock()

    @Test
    fun `request 로깅 - 실패`() {
        val reqLoggingService = HttpRequestLoggingService(IPAddressUtil(), mock(), mock())
        val resLoggingService: ResponseLoggingService = mock()
        val eventPublisher = MockEventPublisher()
        val resLoggingDto = HttpResponseLoggingDto(200)
        `when`(resLoggingService.logging(null)).thenReturn(resLoggingDto)
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isTestSuccess).isFalse()
    }

    @Test
    fun `response 로깅 - 실패`() {
        val reqLoggingService: RequestLoggingService = mock()
        val resLoggingService: ResponseLoggingService = mock()
        val eventPublisher = MockEventPublisher()

        val reqLoggingDto = HttpRequestLoggingDto(UUID.randomUUID(), "", "", "", "", "", "")
        `when`(reqLoggingService.logging()).thenReturn(reqLoggingDto)
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isTestSuccess).isFalse()
    }

    @Test
    fun `request, reponse 로깅 - 실패`() {
        val reqLoggingService = HttpRequestLoggingService(IPAddressUtil(), mock(), mock())
        val resLoggingService = HttpResponseLoggingService()
        val eventPublisher = MockEventPublisher()

        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isTestSuccess).isFalse()
    }


    @Test
    fun `request, reponse 로깅 - 성공`() {
        val reqLoggingService: RequestLoggingService = mock()
        val resLoggingService: ResponseLoggingService = mock()
        val eventPublisher = MockEventPublisher()
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        val reqLoggingDto = HttpRequestLoggingDto(UUID.randomUUID(), "", "", "", "", "", "")
        val resLoggingDto = HttpResponseLoggingDto(200)
        `when`(reqLoggingService.logging()).thenReturn(reqLoggingDto)
        `when`(resLoggingService.logging(null)).thenReturn(resLoggingDto)

        // when
        loggingAspect.logRequestAndResponse(joinPoint)

        // then
        assertThat(eventPublisher.isTestSuccess).isEqualTo(true)
    }

    @Test
    fun `request, reponse 로깅 - 실패(event 발행 중 예외 발생)`() {
        val reqLoggingService: RequestLoggingService = mock()
        val resLoggingService: ResponseLoggingService = mock()
        val eventPublisher = MockExceptionEventPublisher()
        val loggingAspect = ClientLoggingAspect(reqLoggingService, resLoggingService, eventPublisher)

        `when`(reqLoggingService.logging()).thenReturn(HttpRequestLoggingDto(UUID.randomUUID(), "", "", "", "", "", ""))
        `when`(resLoggingService.logging(null)).thenReturn(HttpResponseLoggingDto(200))

        // when
        assertDoesNotThrow {
            loggingAspect.logRequestAndResponse(joinPoint)
        }
    }
}

class MockEventPublisher : ApplicationEventPublisher {
    var isTestSuccess = false
    override fun publishEvent(event: Any) {
        isTestSuccess = true
    }
}

class MockExceptionEventPublisher : ApplicationEventPublisher {
    override fun publishEvent(event: Any) {
        throw Exception()
    }
}