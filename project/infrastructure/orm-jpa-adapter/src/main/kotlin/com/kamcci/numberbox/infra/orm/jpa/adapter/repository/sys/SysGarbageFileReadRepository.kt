package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys

import com.kamcci.numberbox.app.domain.enumeration.sys.GarbageFileType
import com.kamcci.numberbox.app.domain.vo.sys.SysGarbageFileVo
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileReadOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.QSysGarbageFileEntity.sysGarbageFileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.util.sys.SysGarbageFileExpression
import org.springframework.stereotype.Repository

@Repository
class SysGarbageFileReadRepository(
    private val sysGarbageFileExpression: SysGarbageFileExpression
) : SysGarbageFileReadOrmPort, BaseRepository() {
    override fun readAllByType(type: GarbageFileType): List<SysGarbageFileVo> {
        return queryFactory
            .select(sysGarbageFileExpression.ceSysGarbageFileVo())
            .from(sysGarbageFileEntity)
            .where(sysGarbageFileEntity.type.eq(type))
            .fetch()
    }
}