package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

/**
 * 파일 삭제 이벤트 리스너
 */
@Component
class FileDeleteEventListener(
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort,
) {
    @Async
    @EventListener
    fun save(eventDto: FileDeleteDto) {
        sysGarbageFileWriteOrmPort.create(eventDto)
    }
}
