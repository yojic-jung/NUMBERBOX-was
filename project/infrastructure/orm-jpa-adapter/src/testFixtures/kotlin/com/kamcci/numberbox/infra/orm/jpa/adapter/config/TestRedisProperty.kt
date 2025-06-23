package com.kamcci.numberbox.infra.orm.jpa.adapter.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "custom.redis",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true
)
data class TestRedisProperty(
    val port: Int,
)