package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.vo.port.storage.FileNameVo
import com.kamcci.numberbox.app.port.orm.resource.MathResourceImgModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceImgEntity.mathResourceImgEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceImgFactory
import org.springframework.stereotype.Repository

@Repository
class MathResourceImgModifyRepository : MathResourceImgModifyOrmPort, BaseRepository() {
    override fun create(resourceId: Long, imgList: List<FileNameVo>) {
        val resourceEntity = em.find(MathResourceEntity::class.java, resourceId)
        val imgEntityList = MathResourceImgFactory.getSaveEntity(resourceEntity, imgList)
        for (imgEntity in imgEntityList) {
            em.persist(imgEntity)
        }
    }

    override fun deleteByResourceId(resourceId: Long): Long {
        return queryFactory
            .delete(mathResourceImgEntity)
            .where(mathResourceImgEntity.mathResource.id.eq(resourceId))
            .execute()
    }
}