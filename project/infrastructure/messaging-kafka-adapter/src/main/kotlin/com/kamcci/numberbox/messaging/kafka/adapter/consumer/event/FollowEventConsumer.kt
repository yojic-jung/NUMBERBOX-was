package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class FollowEventConsumer {
    private val log = LoggerFactory.getLogger(FollowEventConsumer::class.java)

    @Bean("follow")
    fun like(): Consumer<FollowEvent> {
        return Consumer { event -> log.info("Received follow event: $event") }

    }
}