package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import java.util.function.Consumer

@Component
class CommentEventConsumer {
    private val log = LoggerFactory.getLogger(CommentEventConsumer::class.java)

    @Bean("comment")
    fun comment(): Consumer<CommentEvent> {
        return Consumer { event -> log.info("Received comment event: $event") }

    }
}