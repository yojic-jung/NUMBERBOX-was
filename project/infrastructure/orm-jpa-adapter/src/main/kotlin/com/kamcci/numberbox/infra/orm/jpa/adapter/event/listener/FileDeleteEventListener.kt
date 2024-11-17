package com.kamcci.numberbox.infra.orm.jpa.adapter.event.listener

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class FileDeleteEventListener(
    private val sysGarbageFileModifyOrmPort: SysGarbageFileModifyOrmPort
) : BaseRepository() {
    @Async
    @EventListener
    fun save(eventDto: FileDeleteCreateDto) {
        sysGarbageFileModifyOrmPort.create(eventDto)
    }

    @Async
    @EventListener
    fun save(eventDtoList: List<FileDeleteCreateDto>) {
        eventDtoList.forEach { eventDto ->
            sysGarbageFileModifyOrmPort.create(eventDto)
        }
    }
}