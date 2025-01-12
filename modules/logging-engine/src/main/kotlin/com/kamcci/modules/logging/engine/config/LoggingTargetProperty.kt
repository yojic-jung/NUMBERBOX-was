package com.kamcci.modules.logging.engine.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(
    prefix = "kamcci.logging.request",
    ignoreUnknownFields = false,
    ignoreInvalidFields = true
)
data class LoggingTargetProperty(
    val contentType: List<String>,
    val exceptUri: List<String>?,
    val bodyExceptUri: List<String>?,
)