package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlResponseEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpResponseEvent
import com.kamcci.numberbox.infra.orm.jpa.adapter.mock.MockHwpConvertFileWriteRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HwpConvertCompleteEventListenerTest {
    lateinit var hwpConvertFileWriteRepository: MockHwpConvertFileWriteRepository
    lateinit var eventListener: HwpConvertCompleteEventListener

    @BeforeEach
    fun init() {
        hwpConvertFileWriteRepository = MockHwpConvertFileWriteRepository()
        eventListener = HwpConvertCompleteEventListener(hwpConvertFileWriteRepository)
    }

    @Test
    fun `jsonToHwp 변환 완료 응답 이벤트 수신 - 성공`() {
        // given
        val event = JsonToHwpResponseEvent(1, "anyFileName")

        // when
        eventListener.processConvertResponseEvent(event)

        // then
        assertThat(hwpConvertFileWriteRepository.executeCnt).isOne()
    }

    @Test
    fun `hwpToHtml 변환 완료 응답 이벤트 수신 - 성공`() {
        // given
        val event = HwpToHtmlResponseEvent(1, "anyFileName")

        // when
        eventListener.processConvertResponseEvent(event)

        // then
        assertThat(hwpConvertFileWriteRepository.executeCnt).isOne()
    }
}