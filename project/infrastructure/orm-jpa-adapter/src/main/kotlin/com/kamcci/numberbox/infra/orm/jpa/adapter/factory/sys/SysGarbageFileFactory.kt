package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.SysGarbageFileEntity

object SysGarbageFileFactory {
    fun getSaveEntity(fileDeleteDto: FileDeleteDto) =
        SysGarbageFileEntity().apply {
            type = fileDeleteDto.type
            path = fileDeleteDto.path
            name = fileDeleteDto.name
            failCnt = 0
        }
}