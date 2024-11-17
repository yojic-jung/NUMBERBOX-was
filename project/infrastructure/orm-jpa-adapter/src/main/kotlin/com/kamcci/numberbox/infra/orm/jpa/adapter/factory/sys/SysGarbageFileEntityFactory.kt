package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.SysGarbageFileEntity

object SysGarbageFileEntityFactory {
    fun getSaveEntity(createDto: FileDeleteCreateDto) =
        SysGarbageFileEntity().apply {
            type = createDto.type
            path = createDto.path
            name = createDto.name
        }
}