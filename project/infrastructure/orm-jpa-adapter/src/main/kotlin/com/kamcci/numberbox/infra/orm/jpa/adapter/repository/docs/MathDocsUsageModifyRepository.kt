package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs.MathDocsUsageFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathDocsUsageModifyRepository : MathDocsUsageModifyOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        val saveEntity = MathDocsUsageFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}