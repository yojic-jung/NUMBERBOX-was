package com.kamcci.numberbox.hwp.client.adapter.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.hwp.client.adapter.config.MathHwpProducer
import org.springframework.stereotype.Component


@Component
class HwpConvertEventAdapter(
    private val mathHwpProducer: MathHwpProducer
): HwpConvertEventPort {

    override fun requestHwp(eventDto: JsonToHwpRequestEvent) {
        mathHwpProducer.publish(eventDto)
    }

    override fun requestHtml(eventDto: HwpToHtmlRequestEvent) {
        mathHwpProducer.publish(eventDto)
    }
}