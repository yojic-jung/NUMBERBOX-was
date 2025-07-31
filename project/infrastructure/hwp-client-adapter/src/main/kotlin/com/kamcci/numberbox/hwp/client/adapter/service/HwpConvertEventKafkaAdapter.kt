package com.kamcci.numberbox.hwp.client.adapter.service

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import com.kamcci.numberbox.app.port.hwp.HwpConvertEventPort
import com.kamcci.numberbox.hwp.client.adapter.config.HwpConvertProducerCallback
import com.kamcci.numberbox.hwp.client.adapter.config.KafkaTopicName.HwpToHtmlRequestTopic
import com.kamcci.numberbox.hwp.client.adapter.config.KafkaTopicName.JsonToHwpRequestTopic
import org.springframework.context.annotation.Primary
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component


@Primary
@Component
class HwpConvertEventKafkaAdapter(
    private val kafkaJsonTemplate: KafkaTemplate<String, JsonToHwpRequestEvent>,
    private val kafkaHwpTemplate: KafkaTemplate<String, HwpToHtmlRequestEvent>,
    private val hwpConvertProducerCallback: HwpConvertProducerCallback
) : HwpConvertEventPort {

    override fun requestHwp(eventDto: JsonToHwpRequestEvent) {
        val future = kafkaJsonTemplate.send(JsonToHwpRequestTopic, eventDto.id.toString(), eventDto)
        hwpConvertProducerCallback.attach(future, JsonToHwpRequestTopic, eventDto.id, eventDto)
    }

    override fun requestHtml(eventDto: HwpToHtmlRequestEvent) {
        val future = kafkaHwpTemplate.send(HwpToHtmlRequestTopic, eventDto.id.toString(), eventDto)
        hwpConvertProducerCallback.attach(future, JsonToHwpRequestTopic, eventDto.id, eventDto)
    }
}