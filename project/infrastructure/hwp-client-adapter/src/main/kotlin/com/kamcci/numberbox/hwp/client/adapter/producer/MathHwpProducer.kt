package com.kamcci.numberbox.hwp.client.adapter.producer

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.HWP_TO_HTML_PRODUCE
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.JSON_TO_HWP_PRODUCE
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Supplier

@Component
class MathHwpProducer {
    private val log = LoggerFactory.getLogger(MathHwpProducer::class.java)

    // json to hwp 변환 요청 큐
    private val jsonEventQueue = LinkedBlockingQueue<JsonToHwpRequestEvent>()

    // hwp to html 변환 요청 큐
    private val fileEventQueue = LinkedBlockingQueue<HwpToHtmlRequestEvent>()

    fun publish(eventDto: JsonToHwpRequestEvent) {
        log.info("이벤트 발행 : ${eventDto}")
        jsonEventQueue.offer(eventDto)
    }

    fun publish(eventDto: HwpToHtmlRequestEvent) {
        log.info("이벤트 발행 : ${eventDto}")
        fileEventQueue.offer(eventDto)
    }

    @Bean(JSON_TO_HWP_PRODUCE)
    fun jsonToHwpProducer(): Supplier<JsonToHwpRequestEvent> {
        return Supplier { jsonEventQueue.poll() }
    }

    @Bean(HWP_TO_HTML_PRODUCE)
    fun commentProducer(): Supplier<HwpToHtmlRequestEvent> {
        return Supplier { fileEventQueue.poll() }
    }
}