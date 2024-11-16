package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.resource

import com.kamcci.numberbox.app.domain.dto.resource.MathResourceSaveDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.MathResourceEntity
import java.time.LocalDateTime

object MathResourceFactory {
    fun getSaveEntity(saveDto: MathResourceSaveDto): MathResourceEntity {
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

}