package com.kamcci.numberbox.hwp.client.adapter.consumer

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlResponseEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpResponseEvent
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationEventPublisher

class MathHwpConsumerTest {

    private val mockPublisher = object : ApplicationEventPublisher {
        override fun publishEvent(event: Any) {
            println("이벤트 발행됨: $event")
        }
    }
    private val mathHwpConsumer = MathHwpConsumer(mockPublisher)

    @Test
    fun `jsonToHwp Consumer 처리 - 성공`() {
        val consumer = mathHwpConsumer.jsonToHwp()
        consumer.accept(JsonToHwpResponseEvent(1L, "/test/test.json"))
    }

    @Test
    fun `hwpToHtml Consumer 처리 - 성공`() {
        val consumer = mathHwpConsumer.hwpToHtml()
        consumer.accept(HwpToHtmlResponseEvent(1L, "/test/test.json"))
    }
}