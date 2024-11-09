package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathContentsModifyDto
import com.kamcci.numberbox.app.domain.enumeration.math.ContentsSvcPosbSttsType
import com.kamcci.numberbox.app.domain.enumeration.math.MultiChoiceType
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsEntity
import java.time.LocalDateTime

object MathContentsFactory {
    fun getSaveEntity(
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsModifyDto
    ): MathContentsEntity {
        // 타임 스탬프
        val now = LocalDateTime.now()

        // 객관식, 주관식 여부
        val multiChoiceType =
            if (!contentsCreateDto.choiceAnswer.isNullOrEmpty()) MultiChoiceType.Multiple else MultiChoiceType.Essay

        // 정답 존재여부
        val ansExistStts = !contentsCreateDto.answer.isNullOrEmpty() || !contentsCreateDto.choiceAnswer.isNullOrEmpty()

        // 변형문제 수
        val transConCnt = 0

        val entity = MathContentsEntity().apply {
            unitId = contentsCreateDto.unitId
            typeId = contentsCreateDto.typeId
            memberId = contentsCreateDto.memberId
            contents = contentsCreateDto.contents
            solution = contentsCreateDto.solution
            firNo = contentsCreateDto.firNo
            secNo = contentsCreateDto.secNo
            thrNo = contentsCreateDto.thrNo
            fourNo = contentsCreateDto.fourNo
            fifNo = contentsCreateDto.fifNo
            this.multiChoiceType = multiChoiceType
            answer = contentsCreateDto.answer
            choiceAnswer = contentsCreateDto.choiceAnswer?.joinToString(",")
            quesLevel = contentsCreateDto.quesLevel
            this.ansExistStts = ansExistStts
            svcPosbStts = svcPosbSttsType
            this.transConCnt = transConCnt
            sysCreateDate = now
            sysUpdateDate = now
        }
        return entity
    }

    fun getUpdtEntity(
        entity: MathContentsEntity,
        svcPosbSttsType: ContentsSvcPosbSttsType,
        contentsCreateDto: MathContentsModifyDto
    ): MathContentsEntity {
        // 객관식, 주관식 여부
        val multiChoiceType =
            if (!contentsCreateDto.choiceAnswer.isNullOrEmpty()) MultiChoiceType.Multiple else MultiChoiceType.Essay

        // 정답 존재여부
        val ansExistStts = !contentsCreateDto.answer.isNullOrEmpty() || !contentsCreateDto.choiceAnswer.isNullOrEmpty()

        val newEntity = entity.apply {
            unitId = contentsCreateDto.unitId
            typeId = contentsCreateDto.typeId
            memberId = contentsCreateDto.memberId
            contents = contentsCreateDto.contents
            solution = contentsCreateDto.solution
            firNo = contentsCreateDto.firNo
            secNo = contentsCreateDto.secNo
            thrNo = contentsCreateDto.thrNo
            fourNo = contentsCreateDto.fourNo
            fifNo = contentsCreateDto.fifNo
            this.multiChoiceType = multiChoiceType
            answer = contentsCreateDto.answer
            choiceAnswer = contentsCreateDto.choiceAnswer?.joinToString(",")
            quesLevel = contentsCreateDto.quesLevel
            this.ansExistStts = ansExistStts
            svcPosbStts = svcPosbSttsType
            sysUpdateDate = LocalDateTime.now()
        }
        return newEntity
    }

}