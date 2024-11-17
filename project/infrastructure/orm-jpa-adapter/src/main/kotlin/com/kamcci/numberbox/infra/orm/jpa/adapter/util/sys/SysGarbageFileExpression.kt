package com.kamcci.numberbox.infra.orm.jpa.adapter.util.sys

import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.QSysGarbageFileEntity.sysGarbageFileEntity
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class SysGarbageFileExpression {
    fun ceSysGarbageFileVo() =
        Projections.constructor(
            SysGarbageFileVo::class.java,
            sysGarbageFileEntity.id,
            sysGarbageFileEntity.type,
            sysGarbageFileEntity.path,
            sysGarbageFileEntity.name,
            sysGarbageFileEntity.failCnt,
        )
}