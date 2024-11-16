package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceSaveDto
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceFactory
import org.springframework.stereotype.Repository

@Repository
class MathResourceModifyRepository : MathResourceModifyOrmPort, BaseRepository() {
    override fun create(saveDto: MathResourceSaveDto): Long {
        val saveEntity = MathResourceFactory.getSaveEntity(saveDto)
        em.persist(saveEntity)
        return saveEntity.id
    }
}