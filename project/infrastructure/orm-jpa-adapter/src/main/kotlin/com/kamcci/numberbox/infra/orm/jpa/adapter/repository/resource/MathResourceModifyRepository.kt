package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceFactory
import org.springframework.stereotype.Repository

@Repository
class MathResourceModifyRepository : MathResourceModifyOrmPort, BaseRepository() {
    override fun create(createDto: MathResourceCreateOrmDto): Long {
        val saveEntity = MathResourceFactory.getSaveEntity(createDto)
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun update(updateDto: MathResourceUpdtOrmDto) {
        val originEntity = em.find(MathResourceEntity::class.java, updateDto.resourceId)
        val updateEntity = MathResourceFactory.getUpdateEntity(originEntity, updateDto)
        em.persist(updateEntity)
    }


}