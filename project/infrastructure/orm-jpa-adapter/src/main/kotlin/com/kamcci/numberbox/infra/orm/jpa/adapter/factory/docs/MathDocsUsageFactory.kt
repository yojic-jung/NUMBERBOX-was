package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsUsageCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.MathDocsUsageEntity
import java.util.*

object MathDocsUsageFactory {
    fun getSaveEntity(memberId: UUID, createDto: MathDocsUsageCreateDto): MathDocsUsageEntity {
        return MathDocsUsageEntity().apply {
            contentsIdList = createDto.contentsIdList.toMutableList()
            this.memberId = memberId
            docsGrade = createDto.docsGrade
            docsTitle = createDto.docsTitle
            docsSubTitle = createDto.docsSubTitle
            docsOwner = createDto.docsOwner
        }
    }

}