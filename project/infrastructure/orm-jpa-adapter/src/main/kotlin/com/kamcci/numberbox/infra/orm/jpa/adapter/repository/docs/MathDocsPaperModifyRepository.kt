package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs.MathDocsPaperFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathDocsPaperModifyRepository : MathDocsPaperModifyOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsPapaerCreateDto): Long {
        val saveEntity = MathDocsPaperFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}