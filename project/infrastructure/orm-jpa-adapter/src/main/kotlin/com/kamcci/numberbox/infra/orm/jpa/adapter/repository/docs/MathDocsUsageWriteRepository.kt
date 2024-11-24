package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.app.port.orm.docs.MathDocsUsageWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs.MathDocsUsageFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathDocsUsageWriteRepository : MathDocsUsageWriteOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsUsageCreateDto): Long {
        val saveEntity = MathDocsUsageFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}