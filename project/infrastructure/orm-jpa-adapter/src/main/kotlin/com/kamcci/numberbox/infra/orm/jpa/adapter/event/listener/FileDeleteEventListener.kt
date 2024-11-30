package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class FileDeleteEventListener(
    private val sysGarbageFileWriteOrmPort: SysGarbageFileWriteOrmPort
) : BaseRepository() {
    @Async
    @EventListener
    fun save(eventDto: FileDeleteDto) {
        sysGarbageFileWriteOrmPort.create(eventDto)
    }

    @Async
    @EventListener
    fun save(eventDtoList: List<FileDeleteDto>) {
        eventDtoList.forEach { eventDto ->
            sysGarbageFileWriteOrmPort.create(eventDto)
        }
    }
}