package com.kamcci.numberbox.hwp.client.adapter.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.hwp.client.adapter.producer.MathHwpProducer
import org.junit.jupiter.api.Test

class HwpConvertEventAdapterTest {
    private val hwpConvertEventAdapter = HwpConvertEventAdapter(MathHwpProducer())

    @Test
    fun `jsonToHwp 변환 요청`() {
        val jsonToHwpRequestEvent = JsonToHwpRequestEvent(1L, "/test/test.json")

        hwpConvertEventAdapter.requestHwp(jsonToHwpRequestEvent)
    }


    @Test
    fun `hwpToHtml 변환 요청`() {
        val hwpToHtmlRequestEvent = HwpToHtmlRequestEvent(1L, "/test/test.json")

        hwpConvertEventAdapter.requestHtml(hwpToHtmlRequestEvent)
    }
}