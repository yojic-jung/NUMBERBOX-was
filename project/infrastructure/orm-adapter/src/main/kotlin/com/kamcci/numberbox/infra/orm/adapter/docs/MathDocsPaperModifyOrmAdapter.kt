package com.kamcci.numberbox.infra.orm.adapter.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import com.kamcci.numberbox.app.port.orm.docs.MathDocsPaperModifyOrmPort
import com.kamcci.numberbox.infra.orm.base.BaseRepository
import com.kamcci.numberbox.infra.orm.factory.docs.MathDocsPaperFactory
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MathDocsPaperModifyOrmAdapter : MathDocsPaperModifyOrmPort, BaseRepository() {
    override fun create(memberId: UUID, createDto: MathDocsPapaerCreateDto): Long {
        val saveEntity = MathDocsPaperFactory.getSaveEntity(memberId, createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}