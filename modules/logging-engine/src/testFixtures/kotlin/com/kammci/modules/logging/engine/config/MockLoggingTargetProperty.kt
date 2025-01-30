package com.kammci.modules.logging.engine.config

import com.kamcci.modules.logging.engine.config.LoggingTargetProperty


object MockLoggingTargetProperty {
    fun getLoggingTargetProperty(
        contentType: List<String> = listOf(""),
        exceptUri: List<String>? = null,
        bodyExceptUri: List<String>? = null
    ) = LoggingTargetProperty(contentType, exceptUri, bodyExceptUri)
}