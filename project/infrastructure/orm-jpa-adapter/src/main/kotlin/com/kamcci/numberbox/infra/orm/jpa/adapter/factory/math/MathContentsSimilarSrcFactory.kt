package com.kamcci.numberbox.infra.orm.jpa.adapter.factory.math

import com.kamcci.numberbox.app.domain.dto.math.MathConSimilarSrcCreateDto
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.MathContentsSimilarSrcEntity
import java.time.LocalDateTime

object MathContentsSimilarSrcFactory {

    fun getSaveEntity(
        contentsId: Long,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): MathContentsSimilarSrcEntity {
        // 타임 스탬프
        val now = LocalDateTime.now()
        val entity = MathContentsSimilarSrcEntity().apply {
            this.contentsId = contentsId
            orgSrcRef = similarSrcDto.orgSrcRef
            orgSrcNo = similarSrcDto.orgSrcNo
            orgSrcPage = similarSrcDto.orgSrcPage
            copyrightYear = similarSrcDto.copyrightYear
            mathTypeClassify = similarSrcDto.mathTypeClassify
            sysCreateDate = now
            sysUpdateDate = now
        }

        return entity
    }

    fun getUpdateEntity(
        orgEntity: MathContentsSimilarSrcEntity,
        similarSrcDto: MathConSimilarSrcCreateDto
    ): MathContentsSimilarSrcEntity {
        // 타임 스탬프
        val entity = orgEntity.apply {
            orgSrcRef = similarSrcDto.orgSrcRef
            orgSrcNo = similarSrcDto.orgSrcNo
            orgSrcPage = similarSrcDto.orgSrcPage
            copyrightYear = similarSrcDto.copyrightYear
            mathTypeClassify = similarSrcDto.mathTypeClassify
            sysUpdateDate = LocalDateTime.now()
        }

        return entity
    }

}