package com.kammci.modules.logging.engine.dummy.config

import com.kamcci.modules.logging.engine.config.LoggingTargetProperty


/**
 * 테스트용 데이터
 */
object LoggingDummyData {
    fun getLoggingTargetProperty(
        contentType: List<String> = listOf(""),
        exceptUri: List<String>? = null,
        bodyExceptUri: List<String>? = null
    ) = LoggingTargetProperty(contentType, exceptUri, bodyExceptUri)
}