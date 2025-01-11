package com.kamcci.modules.logging.engine.config

data class LoggingTargetProperty(
    val exceptUri: List<String>,
    val bodyExceptUri: List<String>,
    val contentType: List<String>,
)