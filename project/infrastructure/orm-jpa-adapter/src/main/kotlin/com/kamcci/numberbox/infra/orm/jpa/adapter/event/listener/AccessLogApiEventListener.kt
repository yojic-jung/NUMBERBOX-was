package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository
import jakarta.transaction.Transactional
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

/**
 * 클라이언트 api 요청 로깅 이벤트 리스너
 */
@Component
class AccessLogApiEventListener(
    private val logClientApiRepository: LogClientApiRepository
) {
    @Async
    @EventListener
    @Transactional
    fun handle(loggingEventDto: ClientLoggingInfoEventDto) {
        logClientApiRepository.save(loggingEventDto)
    }
}