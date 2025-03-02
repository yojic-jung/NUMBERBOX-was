package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.modules.logging.control.dto.HttpRequestLoggingDto
import com.kamcci.modules.logging.control.dto.HttpResponseLoggingDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockLogClientApiRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.*

class AccessLogApiEventListenerTest {
    private val logClientApiRepository = MockLogClientApiRepository()
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
        assertThat(logClientApiRepository.executeCnt).isEqualTo(1L)
    }
}