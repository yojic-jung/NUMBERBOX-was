package com.kamcci.numberbox.hwp.client.adapter.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.domain.exception.BusinessServerException
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.hwp.client.adapter.config.KafkaTopicName.HwpToHtmlRequestTopic
import com.kamcci.numberbox.hwp.client.adapter.config.KafkaTopicName.JsonToHwpRequestTopic
import org.apache.kafka.common.errors.TimeoutException
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Primary
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit


@Primary
@Component
class HwpConvertEventKafkaAdapter(
    private val kafkaJsonTemplate: KafkaTemplate<String, JsonToHwpRequestEvent>,
    private val kafkaHwpTemplate: KafkaTemplate<String, HwpToHtmlRequestEvent>,
) : HwpConvertEventPort {

    private val log = LoggerFactory.getLogger(this::class.java)

    override fun requestHwp(eventDto: JsonToHwpRequestEvent) {
        try {
            kafkaJsonTemplate.send(JsonToHwpRequestTopic, eventDto.id.toString(), eventDto)
                .get(6, TimeUnit.SECONDS)
        } catch (e: TimeoutException) {
            log.error("Kafka send timeout", e)
            throw BusinessServerException("kafka 예외 발생")
        } catch (e: ExecutionException) {
            log.error("Kafka execution error", e)
            throw BusinessServerException("kafka 예외 발생")
        } catch (e: Exception) {
            log.error("Kafka unknown error", e)
            throw BusinessServerException("kafka 예외 발생")
        }
    }

    override fun requestHtml(eventDto: HwpToHtmlRequestEvent) {
        try {
            kafkaHwpTemplate.send(HwpToHtmlRequestTopic, eventDto.id.toString(), eventDto)
                .get(6, TimeUnit.SECONDS)
        } catch (e: TimeoutException) {
            log.error("Kafka send timeout", e)
            throw BusinessServerException("kafka 예외 발생")
        } catch (e: ExecutionException) {
            log.error("Kafka execution error", e)
            throw BusinessServerException("kafka 예외 발생")
        } catch (e: Exception) {
            log.error("Kafka unknown error", e)
            throw BusinessServerException("kafka 예외 발생")
        }
    }
}