package com.kamcci.numberbox.hwp.client.adapter.config

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlResponseEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpResponseEvent
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.HWP_TO_HTMl
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.JSON_TO_HWP
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class MathHwpConsumer(
    private val eventPublisher: ApplicationEventPublisher
) {
    @Bean(JSON_TO_HWP)
    fun jsonToHwpConsumer(): Consumer<JsonToHwpResponseEvent> {
        return Consumer { event -> eventPublisher.publishEvent(event) }
    }

    @Bean(HWP_TO_HTMl)
    fun hwpToHtmlProducer(): Consumer<HwpToHtmlResponseEvent> {
        return Consumer { event -> eventPublisher.publishEvent(event) }
    }
}