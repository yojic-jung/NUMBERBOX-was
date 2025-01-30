package com.kammci.modules.logging.engine.stub.common

import org.springframework.context.ApplicationEventPublisher


/**
 * ApplicationEventPublisher 스텁
 */
class MockEventPublisher(
    private val throwException: Boolean
) : ApplicationEventPublisher {
    var isRun = false
    override fun publishEvent(event: Any) {
        if (throwException) throw RuntimeException()
        isRun = true
    }
}