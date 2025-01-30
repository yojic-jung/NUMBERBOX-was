package com.kammci.modules.logging.engine.base

import org.springframework.context.ApplicationEventPublisher


class MockEventPublisher(
    private val throwException: Boolean
) : ApplicationEventPublisher {
    var isRun = false
    override fun publishEvent(event: Any) {
        if (throwException) throw RuntimeException()
        isRun = true
    }
}