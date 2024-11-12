package com.kamcci.numberbox.infra.orm.jpa.adapter.util.resource

import com.kamcci.numberbox.app.domain.vo.resource.MathResourceMenuVo
import com.kamcci.numberbox.app.domain.vo.resource.MathResourceVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceEntity.mathResourceEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.resource.QMathResourceMenuEntity.mathResourceMenuEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class MathResourceExpression {
    fun ceMathResourceMenuVo(): ConstructorExpression<MathResourceMenuVo> =
        Projections.constructor(
            MathResourceMenuVo::class.java,
            mathResourceMenuEntity.id,
            mathResourceMenuEntity.mainCateId,
            mathResourceMenuEntity.mainCateName,
            mathResourceMenuEntity.midCateId,
            mathResourceMenuEntity.midCateName,
            mathResourceMenuEntity.alignOrder,
        )

    fun ceMathResourceVo(): ConstructorExpression<MathResourceVo> =
        Projections.constructor(
            MathResourceVo::class.java,
            mathResourceEntity.id,
            mathResourceEntity.title,
            mathResourceEntity.imgPath,
            mathResourceEntity.imgName,
            mathResourceEntity.pptPath,
            mathResourceEntity.pptName,
            mathResourceEntity.pptPageCnt,
            mathResourceEntity.downCnt,
            mathResourceEntity.sysCreateDate,
            mathResourceEntity.sysUpdateDate,
        )
}