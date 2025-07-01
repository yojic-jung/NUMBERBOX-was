package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

import java.time.Instant

data class FollowEvent(
    val type: FollowEventType,
    val postId: Long,
    val userId: Long,
    val createdAt: Instant
)