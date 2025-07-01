package com.kamcci.numberbox.messaging.kafka.adapter.consumer.event

data class CommentEvent(
    val type: CommentEventType,
    val postId: Long,
    val userId: Long,
    val commentId: Long,
)