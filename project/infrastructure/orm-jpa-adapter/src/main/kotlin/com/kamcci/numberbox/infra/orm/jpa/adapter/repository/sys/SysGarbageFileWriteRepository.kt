package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.sys

import com.kamcci.numberbox.app.domain.dto.sys.FileDeleteDto
import com.kamcci.numberbox.app.port.orm.sys.SysGarbageFileWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.sys.QSysGarbageFileEntity.sysGarbageFileEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.sys.SysGarbageFileFactory
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class SysGarbageFileWriteRepository : SysGarbageFileWriteOrmPort, BaseRepository() {
    override fun create(fileDeleteDto: FileDeleteDto): Long {
        val saveEntity = SysGarbageFileFactory.getSaveEntity(fileDeleteDto)
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun create(fileDeleteDtoList: List<FileDeleteDto>) {
        fileDeleteDtoList.forEach {
            val saveEntity = SysGarbageFileFactory.getSaveEntity(it)
            em.persist(saveEntity)
        }
    }

    override fun deleteById(id: List<Long>) {
        queryFactory
            .delete(sysGarbageFileEntity)
            .where(sysGarbageFileEntity.id.`in`(id))
            .execute()
    }

    override fun incrementFailCntById(id: List<Long>) {
        queryFactory
            .update(sysGarbageFileEntity)
            .set(sysGarbageFileEntity.failCnt, sysGarbageFileEntity.failCnt.add(1))
            .set(sysGarbageFileEntity.sysUpdateDate, LocalDateTime.now())
            .where(sysGarbageFileEntity.id.`in`(id))
            .execute()
    }
}