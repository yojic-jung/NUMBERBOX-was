package com.kamcci.numberbox.infra.orm.jpa.adapter.util.math

import com.kamcci.numberbox.app.domain.vo.math.MathFormulaKeyVo
import com.kamcci.numberbox.infra.orm.jpa.adapter.entity.math.QMathFormulaKeyEntity.mathFormulaKeyEntity
import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import org.springframework.stereotype.Component

@Component
class MathFormulaExpression {
    fun ceMathFormulaKeyVo(): ConstructorExpression<MathFormulaKeyVo> =
        Projections.constructor(
            MathFormulaKeyVo::class.java,
            mathFormulaKeyEntity.id,
            mathFormulaKeyEntity.formulOrder,
            mathFormulaKeyEntity.formulName,
            mathFormulaKeyEntity.formulUi,
            mathFormulaKeyEntity.shortcutKey,
            mathFormulaKeyEntity.latexGrammer,
            mathFormulaKeyEntity.nbGrammer,
            mathFormulaKeyEntity.guide,
            mathFormulaKeyEntity.shortcutKeycode,
            mathFormulaKeyEntity.texGrammer,
            mathFormulaKeyEntity.lineChange,
            mathFormulaKeyEntity.classification,
        )

}