package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.log

import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.log.LogClientApiEntity
import jakarta.transaction.Transactional
import org.springframework.stereotype.Repository

@Repository
class LogClientApiRepository : BaseRepository() {

    @Transactional
    fun save(saveEntity: LogClientApiEntity): Long {
        em.persist(saveEntity)
        return saveEntity.id
    }
}