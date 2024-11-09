package com.kamcci.numberbox.infra.orm.factory.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPapaerCreateDto
import com.kamcci.numberbox.infra.orm.entity.docs.MathDocsPaperEntity
import java.util.*

object MathDocsPaperFactory {
    fun getSaveEntity(memberId: UUID, createDto: MathDocsPapaerCreateDto): MathDocsPaperEntity {
        return MathDocsPaperEntity().apply {
            this.memberId = memberId
            contentsIdList = createDto.contentsIdList
            docsGrade = createDto.docsGrade
            docsTitle = createDto.docsTitle
            docsSubTitle = createDto.docsSubTitle
            docsOwner = createDto.docsOwner
            docsErrStts = createDto.docsErrStts
        }
    }
}