package com.kamcci.numberbox.infra.orm.jpa.adapter.repository.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.app.port.orm.resource.MathResourceModifyOrmPort
import com.kamcci.numberbox.infra.orm.jpa.adapter.base.BaseRepository
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceCateFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceFactory
import com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource.MathResourceImgFactory
import org.springframework.stereotype.Repository

@Repository
class MathResourceModifyRepository : MathResourceModifyOrmPort, BaseRepository() {
    override fun create(createDto: MathResourceCreateOrmDto): Long {
        val saveEntity = MathResourceFactory.getSaveEntity(createDto)

        // 카테고리 설정
        val cateEntityList = MathResourceCateFactory.getSaveEntity(saveEntity, createDto.cateList)
        saveEntity.mathResourceCate = cateEntityList

        // 슬라이드 이미지 설정
        val imgEntityList = MathResourceImgFactory.getSaveEntity(saveEntity, createDto.imgList)
        saveEntity.mathResourceImg = imgEntityList

        // 영속화
        em.persist(saveEntity)
        return saveEntity.id
    }

    override fun update(updateDto: MathResourceUpdtOrmDto) {
        val originEntity = em.find(MathResourceEntity::class.java, updateDto.resourceId)
        val updateEntity = MathResourceFactory.getUpdateEntity(originEntity, updateDto)

        // 카테고리 변경
        updateEntity.mathResourceCate.forEach { em.remove(it) }
        val cateEntityList = MathResourceCateFactory.getUpdateEntity(updateEntity, updateDto.cateList)
        updateEntity.mathResourceCate = cateEntityList

        // 슬라이드 이미지 변경
        if (updateDto.imgList.isNotEmpty()) {
            updateEntity.mathResourceImg.forEach { em.remove(it) }
            val imgEntityList = MathResourceImgFactory.getUpdateEntity(updateEntity, updateDto.imgList)
            updateEntity.mathResourceImg = imgEntityList
        }

        // 영속화
        em.persist(updateEntity)
    }

}