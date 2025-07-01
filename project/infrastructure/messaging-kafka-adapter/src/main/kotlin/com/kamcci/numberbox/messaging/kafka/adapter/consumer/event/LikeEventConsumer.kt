package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class LikeEventConsumer {
    private val log = LoggerFactory.getLogger(LikeEventConsumer::class.java)

    @Bean("like")
    fun like(): Consumer<LikeEvent> {
        return Consumer { event -> log.info("Received like event: $event") }

    }
}