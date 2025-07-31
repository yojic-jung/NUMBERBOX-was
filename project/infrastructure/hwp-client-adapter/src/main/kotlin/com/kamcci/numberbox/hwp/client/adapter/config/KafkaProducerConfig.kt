package com.kamcci.numberbox.hwp.client.adapter.config

import com.kamcci.numberbox.app.domain.dto.hwp.HwpToHtmlRequestEvent
import com.kamcci.numberbox.app.domain.dto.hwp.JsonToHwpRequestEvent
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaProducerConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    bootstrapServers: String
) {
    // 문서변환 요청 producer 설정
    private val convertProps = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers,
        // 응답 성공 여부 구분
        ProducerConfig.ACKS_CONFIG to "all",               // 리더+팔로워 복제 성공 후 응답
        // 중복 허용 여부
        ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG to false,
        // 배치
        ProducerConfig.BATCH_SIZE_CONFIG to 16000,         // 배치 전송 크기
        ProducerConfig.LINGER_MS_CONFIG to 1000,           // 최대 대기 시간
        ProducerConfig.BUFFER_MEMORY_CONFIG to 33554432,   // 프로듀서 내부 버퍼 크기
        // 재처리 설정
        ProducerConfig.RETRIES_CONFIG to 1,                // 재시도 횟수
        ProducerConfig.RETRY_BACKOFF_MS_CONFIG to 500,     // 재시도간 대기 시간
        ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG to 2000,  // 단일 요청에 대한 응답 최대 대기 시간
        ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG to 5000, // 전체 전송 제한 시간
        // 직렬화
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java.name,
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to JsonSerializer::class.java.name,
        // 압축 타입 설정
        ProducerConfig.COMPRESSION_TYPE_CONFIG to "lz4"
    )

    @Bean
    fun kafkaJsonTemplate(): KafkaTemplate<String, JsonToHwpRequestEvent> {
        val producerFactory = DefaultKafkaProducerFactory<String, JsonToHwpRequestEvent>(convertProps)
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun kafkaHwpTemplate(): KafkaTemplate<String, HwpToHtmlRequestEvent> {
        val producerFactory = DefaultKafkaProducerFactory<String, HwpToHtmlRequestEvent>(convertProps)
        return KafkaTemplate(producerFactory)
    }
}