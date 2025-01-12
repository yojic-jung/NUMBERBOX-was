package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log.LogClientApiEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class AccessLogApiEventListener(
    private val logClientApiRepository: LogClientApiRepository
) {
    @Async
    @EventListener
    fun handle(loggingEventDto: ClientLoggingInfoEventDto) {
        logClientApiRepository.save(
            LogClientApiEntity().apply {
                memberId = loggingEventDto.reqLoggingDto.memberId
                browser = loggingEventDto.reqLoggingDto.browser
                os = loggingEventDto.reqLoggingDto.os
                ip = loggingEventDto.reqLoggingDto.ip
                httpMethod = loggingEventDto.reqLoggingDto.method
                uri = loggingEventDto.reqLoggingDto.uri
                responseCode = loggingEventDto.resLoggingDto?.httpStatus
                requestBody = loggingEventDto.reqLoggingDto.reqBody
            }
        )
    }
}