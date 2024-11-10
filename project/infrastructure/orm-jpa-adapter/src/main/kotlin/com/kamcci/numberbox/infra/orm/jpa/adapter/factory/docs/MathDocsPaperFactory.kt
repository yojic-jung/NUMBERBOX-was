package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.docs.MathDocsPaperEntity
import java.util.*

object MathDocsPaperFactory {
    fun getSaveEntity(memberId: UUID, createDto: MathDocsPaperCreateDto): MathDocsPaperEntity {
        return MathDocsPaperEntity().apply {
            this.memberId = memberId
            contentsIdList = createDto.contentsIdList.toMutableList()
            docsGrade = createDto.docsGrade
            docsTitle = createDto.docsTitle
            docsSubTitle = createDto.docsSubTitle
            docsOwner = createDto.docsOwner
            docsStts = createDto.docsStts
        }
    }
}