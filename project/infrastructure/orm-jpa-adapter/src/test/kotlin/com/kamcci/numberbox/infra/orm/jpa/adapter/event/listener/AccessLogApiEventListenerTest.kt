package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.util.*

class AccessLogApiEventListenerTest {
    private val logClientApiRepository: LogClientApiRepository = mock()

    private val accessLogApiEventListener = AccessLogApiEventListener(logClientApiRepository)

    @Test
    fun `이벤트 리스너 동작 - 성공`() {
        // given
        val requestLoggingDto = HttpRequestLoggingDto(
            memberId = UUID.randomUUID(),
            browser = "window",
            os = "mac",
            ip = "127.0.0.1",
            method = "POST",
            uri = "/example",
            reqBody = "{sfjl:dsjf}"
        )
        val responseLoggingDto = HttpResponseLoggingDto(200)
        val loggingDto = ClientLoggingInfoEventDto(requestLoggingDto, responseLoggingDto)

        // when
        accessLogApiEventListener.handle(loggingDto)

        // then -> 이벤트 리스너 동작 확인
        verify(logClientApiRepository).save(loggingDto)

    }
}