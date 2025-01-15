package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log.LogClientApiEntity
import org.springframework.stereotype.Repository

@Repository
class LogClientApiRepository : BaseRepository() {
    fun save(loggingEventDto: ClientLoggingInfoEventDto): Long {
        val saveEntity = LogClientApiEntity().apply {
            memberId = loggingEventDto.reqLoggingDto.memberId
            browser = loggingEventDto.reqLoggingDto.browser
            os = loggingEventDto.reqLoggingDto.os
            ip = loggingEventDto.reqLoggingDto.ip
            httpMethod = loggingEventDto.reqLoggingDto.method
            uri = loggingEventDto.reqLoggingDto.uri
            responseCode = loggingEventDto.resLoggingDto.httpStatus
            requestBody = loggingEventDto.reqLoggingDto.reqBody
        }
        em.persist(saveEntity)
        return saveEntity.id
    }
}