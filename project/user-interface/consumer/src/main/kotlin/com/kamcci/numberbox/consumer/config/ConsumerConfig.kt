package com.kamcci.numberbox.consumer.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.CommonErrorHandler
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.listener.DefaultErrorHandler
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.util.backoff.FixedBackOff
import java.net.SocketTimeoutException


@Configuration
@EnableConfigurationProperties(
    value = [ConsumerProperty::class]
)
class ConsumerConfig {

    companion object {
        const val CONSUMER_GROUP_ID = "numberbox.convert.complete.dev"
    }

    private val log = LoggerFactory.getLogger(this::class.java)

    @Value("\${spring.kafka.bootstrap-servers}")
    lateinit var kafkaUrl: String

    /**
     * ConsumerFactory 설정
     */
    @Bean
    fun consumerFactory(): ConsumerFactory<String, Any> {
        val props = mapOf(
            // 카프카 서버 url
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaUrl,
            // group id 설정
            ConsumerConfig.GROUP_ID_CONFIG to CONSUMER_GROUP_ID,
            // 직렬화 및 역직렬화
            ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG to StringDeserializer::class.java,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG to JsonDeserializer::class.java,
            JsonDeserializer.TRUSTED_PACKAGES to "*" // 직렬화 신뢰 패키지
        )
        return DefaultKafkaConsumerFactory(props)
    }

    /**
     * Kafka Container 설정
     */
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> {
        return ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            consumerFactory = consumerFactory()
            setCommonErrorHandler(errorHandler())
            setConcurrency(3) // 컨슈머 3개 등록

            /**
             * Ack 설정
             *
             * | AckMode            | 설명                                              |
             * | ------------------ | ----------------------------------------------- |
             * | `RECORD`           | 메시지 1건 처리 후 즉시 오프셋 커밋 (**신뢰성 우선**)              |
             * | `BATCH`            | 배치로 받은 여러 메시지 모두 처리한 후 한 번만 커밋                  |
             * | `TIME`             | 일정 시간 간격으로 커밋                                   |
             * | `COUNT`            | 일정 개수만큼 처리한 후 커밋                                |
             * | `MANUAL`           | 개발자가 직접 ack 호출 (`Acknowledgment.acknowledge()`) |
             * | `MANUAL_IMMEDIATE` | 수동 ack + 즉시 커밋                                  |
             *
             */
            containerProperties.ackMode = ContainerProperties.AckMode.RECORD
        }
    }


    /**
     * 에러 핸들러 설정
     * - 동기-blocking -> 기존 토픽에 재시도 하므로 다음 메시지 block됨
     */
    @Bean
    fun errorHandler(): CommonErrorHandler {
        val fixedBackOff = FixedBackOff(1000L, 3)
        val errorHandler = DefaultErrorHandler(
            { consumerRecord, e ->
                log.warn("[kafka consumer exception] key ${consumerRecord.key()}, value: ${consumerRecord.value()}, exception: ${e?.message}")
            },
            fixedBackOff
        )
        errorHandler.addRetryableExceptions(SocketTimeoutException::class.java)
        errorHandler.addNotRetryableExceptions(NullPointerException::class.java)
        return errorHandler
    }

}