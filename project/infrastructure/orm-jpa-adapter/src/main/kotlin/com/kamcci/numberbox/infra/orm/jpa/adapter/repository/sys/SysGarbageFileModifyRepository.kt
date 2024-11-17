package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteCreateDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.QSysGarbageFileEntity.sysGarbageFileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.sys.SysGarbageFileEntityFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SysGarbageFileModifyRepository : SysGarbageFileModifyOrmPort, BaseRepository() {
    override fun create(createDto: FileDeleteCreateDto): Long {
        val saveEntity = SysGarbageFileEntityFactory.getSaveEntity(createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun create(createDtoList: List<FileDeleteCreateDto>) {
        createDtoList.forEach {
            val saveEntity = SysGarbageFileEntityFactory.getSaveEntity(it)
            em.persist(saveEntity)
        }
    }

    override fun deleteById(id: Long) {
        queryFactory
            .delete(sysGarbageFileEntity)
            .where(sysGarbageFileEntity.id.eq(id))
            .execute()
    }

    override fun updateFailCntById(id: Long, failCnt: Int) {
        queryFactory
            .update(sysGarbageFileEntity)
            .set(sysGarbageFileEntity.failCnt, failCnt)
            .set(sysGarbageFileEntity.sysUpdateDate, LocalDateTime.now())
            .where(sysGarbageFileEntity.id.eq(id))
            .execute()
    }
}