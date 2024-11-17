package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.port.orm.resource.MathResourceCateModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceCateEntity.mathResourceCateEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceCateFactory
import org.springframework.stereotype.Repository

@Repository
class MathResourceCateModifyRepository : MathResourceCateModifyOrmPort, BaseRepository() {

    override fun create(resourceId: Long, cateList: List<String>) {
        val resourceEntity = em.find(MathResourceEntity::class.java, resourceId)
        val cateEntityList = MathResourceCateFactory.getSaveEntity(resourceEntity, cateList)
        for (cateEntity in cateEntityList) {
            em.persist(cateEntity)
        }
    }

    override fun deleteByResourceId(resourceId: Long): Long {
        return queryFactory
            .delete(mathResourceCateEntity)
            .where(mathResourceCateEntity.mathResource.id.eq(resourceId))
            .execute()
    }
}