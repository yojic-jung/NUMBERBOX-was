package com.kamcci.numberbox.infra.orm.jpa.adapter.util.math

import com.kamcci.numberbox.app.domain.vo.math.MathCategoryTypeVo
import com.kamcci.numberbox.app.domain.vo.math.MathCategoryUnitVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryTypeEntity.mathCategoryTypeEntity
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathCategoryUnitEntity.mathCategoryUnitEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class MathCategoryExpression {
    fun ceMathCategoryUnitVo(): ConstructorExpression<MathCategoryUnitVo> =
        Projections.constructor(
            MathCategoryUnitVo::class.java,
            mathCategoryUnitEntity.id,
            mathCategoryUnitEntity.subject,
            mathCategoryUnitEntity.firUnit,
            mathCategoryUnitEntity.secUnit,
            mathCategoryUnitEntity.thrUnit
        )

    fun ceMathCategoryTypeVo(): ConstructorExpression<MathCategoryTypeVo> =
        Projections.constructor(
            MathCategoryTypeVo::class.java,
            mathCategoryTypeEntity.mathTypeDomain.unitId,
            mathCategoryTypeEntity.mathTypeDomain.typeId,
            mathCategoryTypeEntity.quesType,
            mathCategoryTypeEntity.typeOrder,
        )
}