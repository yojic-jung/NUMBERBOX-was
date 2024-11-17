package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceCreateOrmDto
import com.kamcci.numberbox.app.domain.dto.resource.MathResourceUpdtOrmDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import java.time.LocalDateTime

object MathResourceFactory {
    fun getSaveEntity(saveDto: MathResourceCreateOrmDto): MathResourceEntity {
        val now = LocalDateTime.now()
        return MathResourceEntity().apply {
            memberId = saveDto.memberId
            title = saveDto.title
            imgPath = saveDto.imgPath
            imgName = saveDto.imgName
            pptPath = saveDto.pptFilePath
            pptName = saveDto.pptFileName
            pptPageCnt = saveDto.pptPageCnt
            sysCreateDate = now
            sysUpdateDate = now
        }
    }

    fun getUpdateEntity(originEntity: MathResourceEntity, updateDto: MathResourceUpdtOrmDto): MathResourceEntity {
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