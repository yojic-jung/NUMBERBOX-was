package com.kamcci.numberbox.app.service.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.system_construction.UseCase
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileReadOrmPort
import com.kamcci.numberbox.app.usecase.sys.SysGarbageFileReadUseCase

@UseCase
class SysGarbageFileReadService(
    private val sysGarbageFileReadOrmPort: SysGarbageFileReadOrmPort
) : SysGarbageFileReadUseCase {
    override fun readAllByType(type: GarbageFileType, limit: Long): List<SysGarbageFileVo> =
        sysGarbageFileReadOrmPort.readAllByType(type, limit)
}