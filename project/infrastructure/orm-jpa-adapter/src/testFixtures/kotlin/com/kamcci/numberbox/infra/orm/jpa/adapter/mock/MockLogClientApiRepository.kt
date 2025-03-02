package com.kamcci.numberbox.infra.orm.jpa.adapter.mock

import com.kamcci.modules.logging.control.dto.ClientLoggingInfoEventDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log.LogClientApiRepository

class MockLogClientApiRepository : LogClientApiRepository() {
    var executeCnt = 0
    override fun save(loggingEventDto: ClientLoggingInfoEventDto): Long {
        executeCnt++
        return 1L
    }
}