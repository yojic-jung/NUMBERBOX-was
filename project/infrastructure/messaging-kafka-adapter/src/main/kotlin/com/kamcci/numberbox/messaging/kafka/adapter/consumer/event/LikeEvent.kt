package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

import java.time.Instant

data class LikeEvent(
    val type: CommentEventType,
    val postId: Long,
    val userId: Long,
    val createdAt: Instant
)