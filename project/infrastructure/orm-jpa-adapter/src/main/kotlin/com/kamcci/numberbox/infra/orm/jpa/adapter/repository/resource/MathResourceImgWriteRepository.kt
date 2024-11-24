package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.port.orm.resource.MathResourceImgWriteOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceImgEntity.mathResourceImgEntity
import org.springframework.stereotype.Repository

@Repository
class MathResourceImgWriteRepository : MathResourceImgWriteOrmPort, BaseRepository() {
    override fun deleteByResourceId(resourceId: Long): Long {
        return queryFactory
            .delete(mathResourceImgEntity)
            .where(mathResourceImgEntity.mathResource.id.eq(resourceId))
            .execute()
    }
}