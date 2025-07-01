package com.kamcci.numberbox.hwp.client.adapter.config

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.HWP_TO_HTMl
import com.kamcci.numberbox.hwp.client.adapter.config.EventName.JSON_TO_HWP
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.concurrent.LinkedBlockingQueue
import java.util.function.Supplier

@Component
class MathHwpProducer {
    // json to hwp 변환 요청 큐
    private val jsonEventQueue = LinkedBlockingQueue<JsonToHwpRequestEvent>()
    // hwp to html 변환 요청 큐
    private val fileEventQueue = LinkedBlockingQueue<HwpToHtmlRequestEvent?>()

    fun publish(eventDto:JsonToHwpRequestEvent ) {
        jsonEventQueue.offer(eventDto)
    }

    fun publish(eventDto:HwpToHtmlRequestEvent ) {
        fileEventQueue.offer(eventDto)
    }

    @Bean(JSON_TO_HWP)
    fun jsonToHwpProducer(): Supplier<JsonToHwpRequestEvent?>? {
        return Supplier { jsonEventQueue.poll() } // 주기적으로 큐에서 꺼내어 Kafka로 발행
    }

    @Bean(HWP_TO_HTMl)
    fun commentProducer(): Supplier<HwpToHtmlRequestEvent?>? {
        return Supplier { fileEventQueue.poll() } // 주기적으로 큐에서 꺼내어 Kafka로 발행
    }
}