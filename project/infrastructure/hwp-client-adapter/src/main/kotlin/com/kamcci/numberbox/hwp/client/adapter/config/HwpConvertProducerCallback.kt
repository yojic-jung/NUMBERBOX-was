package com.kamcci.numberbox.hwp.client.adapter.config

import com.kamcci.numberbox.app.domain.dto.hwp.HwpConvertRequestResultEvent
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class HwpConvertProducerCallback(
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    private val log = LoggerFactory.getLogger(this::class.java)
    fun <T, V> attach(
        future: CompletableFuture<SendResult<T, V>>,
        topic: String,
        key: Long,
        value: V
    ) {
        future.whenComplete { result, ex ->
            if (ex != null) {
                handleFailure(ex, topic, key)
            } else {
                handleSuccess(key, result)
            }
        }
    }

    private fun handleFailure(ex: Throwable, topic: String, key: Long) {
        // 실패 처리
        applicationEventPublisher.publishEvent(HwpConvertRequestResultEvent(key, false))
        log.error("[Kafka send fail] topic: $topic, key: $key, exception: $ex")
    }

    private fun <T, V> handleSuccess(key: Long, result: SendResult<T, V>) {
        // 성공 처리
        applicationEventPublisher.publishEvent(HwpConvertRequestResultEvent(key, true))
        log.info("[Kafka send success] key: $key, result: $result")
    }
}