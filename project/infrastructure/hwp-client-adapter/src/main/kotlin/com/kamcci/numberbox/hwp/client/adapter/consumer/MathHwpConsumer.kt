package com.kamcci.numberbox.hwp.client.adapter.consumer

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlResponseEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpResponseEvent
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.HWP_TO_HTML_CONSUME
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.JSON_TO_HWP_CONSUME
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class MathHwpConsumer(
    private val eventPublisher: ApplicationEventPublisher
) {
    private val log = LoggerFactory.getLogger(MathHwpConsumer::class.java)

    @Bean(JSON_TO_HWP_CONSUME)
    fun jsonToHwp(): Consumer<JsonToHwpResponseEvent> {
        return Consumer { event ->
            log.info("event 소비 : ${event}")
            eventPublisher.publishEvent(event)
        }
    }

    @Bean(HWP_TO_HTML_CONSUME)
    fun hwpToHtml(): Consumer<HwpToHtmlResponseEvent> {
        return Consumer { event ->
            log.info("event 소비 : ${event}")
            eventPublisher.publishEvent(event)
        }
    }
}