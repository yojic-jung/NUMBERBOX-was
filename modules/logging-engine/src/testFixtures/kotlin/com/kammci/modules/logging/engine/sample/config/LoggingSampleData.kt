package com.kammci.modules.logging.engine.sample.config

import com.kamcci.modules.logging.engine.config.LoggingTargetProperty


/**
 * 테스트용 데이터
 */
object LoggingSampleData {
    fun getLoggingTargetProperty(
        contentType: List<String> = listOf(""),
        exceptUri: List<String>? = null,
        bodyExceptUri: List<String>? = null
    ) = LoggingTargetProperty(contentType, exceptUri, bodyExceptUri)
}