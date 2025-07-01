package com.kamcci.numberbox.infra.redis.adapter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "redis.server",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true
)
data class RedisServerProperty(
    val ip: String,
    val port: String,
)
