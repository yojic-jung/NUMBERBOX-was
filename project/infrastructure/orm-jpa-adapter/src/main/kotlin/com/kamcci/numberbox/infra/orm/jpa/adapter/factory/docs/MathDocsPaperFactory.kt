package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.docs

import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperCreateDto
import com.kamcci.numberbox.app.domain.dto.docs.MathDocsPaperUpdtDto
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
            docsStts = createDto.docsErrStts
        }
    }

    fun getUpdtEntity(
        orgEntity: MathDocsPaperEntity,
        updtDto: MathDocsPaperUpdtDto
    ): MathDocsPaperEntity {
        return orgEntity.apply {
            contentsIdList = updtDto.contentsIdList.toMutableList()
            docsGrade = updtDto.docsGrade
            docsTitle = updtDto.docsTitle
            docsSubTitle = updtDto.docsSubTitle
            docsOwner = updtDto.docsOwner
            docsStts = updtDto.docsErrStts
        }
    }
}