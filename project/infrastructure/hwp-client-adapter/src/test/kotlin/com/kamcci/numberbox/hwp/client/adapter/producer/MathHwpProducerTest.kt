package com.kamcci.numberbox.hwp.client.adapter.producer

import org.junit.jupiter.api.Test

class MathHwpProducerTest {
    private val mathHwpProducer = MathHwpProducer()

    @Test
    fun `jsonToHwp Producer 처리 - 성공`() {
        val producer = mathHwpProducer.jsonToHwpProducer()
        producer.get()
    }

    @Test
    fun `hwpToHtml Producer 처리 - 성공`() {
        val producer = mathHwpProducer.hwpToHtmlProducer()
        producer.get()
    }
}