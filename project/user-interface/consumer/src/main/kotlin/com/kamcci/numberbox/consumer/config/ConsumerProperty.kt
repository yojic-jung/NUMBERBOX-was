package com.kamcci.numberbox.consumer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "hwp.server",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true,
)
data class ConsumerProperty(
    val ip: String,
    val port: Int
)