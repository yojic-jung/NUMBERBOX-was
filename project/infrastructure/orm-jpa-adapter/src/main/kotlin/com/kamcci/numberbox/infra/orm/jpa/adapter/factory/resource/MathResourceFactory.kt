package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import java.time.LocalDateTime

object MathResourceFactory {
    fun getSaveEntity(createDto: MathResourceCreateDto): MathResourceEntity {
        val now = LocalDateTime.now()
        return MathResourceEntity().apply {
            memberId = createDto.memberId
            title = createDto.title
            imgPath = createDto.imgPath
            imgName = createDto.imgName
            pptPath = createDto.pptFilePath
            pptName = createDto.pptFileName
            pptPageCnt = createDto.pptPageCnt
            sysCreateDate = now
            sysUpdateDate = now
        }
    }

    fun getUpdateEntity(originEntity: MathResourceEntity, updateDto: MathResourceUpdateDto): MathResourceEntity {
        return originEntity.apply {
            title = updateDto.title
            imgPath = updateDto.imgPath ?: this.imgPath
            imgName = updateDto.imgName ?: this.imgName
            pptPath = updateDto.pptFilePath ?: this.pptPath
            pptName = updateDto.pptFileName ?: this.pptName
            pptPageCnt = updateDto.pptPageCnt ?: this.pptPageCnt
            sysUpdateDate = LocalDateTime.now()
        }
    }

}